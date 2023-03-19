package com.example.androidconcepts.rxJava.ownImpl

import com.example.androidconcepts.common.UiThreadPoster
import java.util.concurrent.Callable
import java.util.concurrent.CountDownLatch

class ReactObservable<T>(val data: ReactObservableData<T>) {

    interface Observer<T> {
        fun onNext(data: T)
        fun onError(throwable: Throwable)
    }

    private val observers = mutableListOf<Observer<T>>()
    private val uiThreadPoster = UiThreadPoster()

    fun subscribe(observer: Observer<T>) {
        observers.add(observer)

        Thread {
            try {
                val result: T = data.callable.call()
                notifyObserversOfSuccess(result)
            } catch (e: Exception) {
                notifyObserversOfException(e)
            }
        }.start() // start emitting only when subscribed i.e cold stream.
    }

    private fun notifyObserversOfSuccess(result: T) {
        uiThreadPoster.post {
            observers.forEach {
                it.onNext(result)
            }
        }
    }

    private fun notifyObserversOfException(e: Exception) {
        uiThreadPoster.post {
            observers.forEach {
                it.onError(Throwable(e.message))
            }
        }
    }


    // ---------- operators -----------

    companion object {
        fun <T> fromCallable(callable: Callable<T>): ReactObservable<T> {
            return ReactObservable(
                ReactObservableData(callable)
            )
        }
    }

    data class ReactObservableData<T>(
        val callable: Callable<T>
    )
}

fun <T,K> ReactObservable<T>.flatMap(mapper: (T) -> ReactObservable<K>): ReactObservable<K> {
    return ReactObservable.fromCallable { // return callable for the a new observable

        // bg thread

        val parentObs = this
        val latch = CountDownLatch(1)

        var result: ReactObservable<K>? = null

        // get the data from observable, map to new observable and notify the latch.
        parentObs.subscribe(object : ReactObservable.Observer<T> {
            override fun onNext(data: T) {
                result = mapper.invoke(data)
                latch.countDown()
            }

            override fun onError(throwable: Throwable) {
                /* no-op */
            }
        })

        latch.await()

        return@fromCallable result!!.data.callable.call()
    }
}