package com.example.androidconcepts.rxJava.ownImpl

import com.example.androidconcepts.common.UiThreadPoster
import java.util.concurrent.Callable
import java.util.concurrent.CountDownLatch

class ReactObservable<T> constructor(val data: ReactObservableData<T>) {

    interface Observer<T> {
        fun onNext(data: T)
        fun onError(throwable: Throwable)
    }

    private val observers = mutableListOf<Observer<T>>()
    private lateinit var uiThreadPoster: UiThreadPoster

    fun setUiThreadPoster(uiThreadPoster: UiThreadPoster): ReactObservable<T> {
        this.uiThreadPoster = uiThreadPoster
        return this
    }

    fun subscribe(observer: Observer<T>) {

        // if UiThreadPoster is not set using setter method then this is always not initialized,
        // use TestDouble for unit tests.
        if (!::uiThreadPoster.isInitialized)
            uiThreadPoster = data.uiThreadPoster ?: UiThreadPoster()

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

    fun <K> flatMap(mapper: (T) -> ReactObservable<K>): ReactObservable<K> {
        val callable = Callable {

            // bg thread

            val parentObs = this
            val latch = CountDownLatch(1)

            var result: ReactObservable<K>? = null

            // get the data from observable, map to new observable and notify the latch.
            parentObs.subscribe(object : Observer<T> {
                override fun onNext(data: T) {
                    result = mapper.invoke(data)
                    latch.countDown()
                }

                override fun onError(throwable: Throwable) {
                    latch.countDown()
                }
            })

            latch.await()

            return@Callable result!!.data.callable.call()
        }

        // when subscribe is called on this returned ReactObservable then this callable is called
        // which internally subscribes to the parent observable.
        return fromCallable(
            callable = callable,
            uiThreadPoster = uiThreadPoster // pass current threadPoster to new observables
        )
    }

    companion object {
        fun <T> fromCallable(
            callable: Callable<T>,
            uiThreadPoster: UiThreadPoster? = null
        ): ReactObservable<T> {
            return ReactObservable(
                ReactObservableData(callable, uiThreadPoster)
            )
        }
    }

    data class ReactObservableData<T>(
        val callable: Callable<T>,
        val uiThreadPoster: UiThreadPoster? = null
    )
}