package com.example.androidconcepts.rxJava.ownImpl

import com.example.androidconcepts.rxJava.ownImpl.ReactObservable.*
import io.reactivex.rxjava3.core.Observable
import java.util.concurrent.Callable

class UsageSample1 {

    private fun fetch(): ReactObservable<String> {
        return ReactObservable.fromCallable(Callable<String> {
            Thread.sleep(2000L)
            return@Callable "result success!"
        })
    }

    private fun start1() {
        fetch().subscribe(object : Observer<String> {
            override fun onNext(data: String) {
                /* no-op */
            }

            override fun onError(throwable: Throwable) {
                /* no-op */
            }
        })

        fetch().flatMap { mapperObs(it) }
            .subscribe(object : Observer<Int> {
                override fun onNext(data: Int) {
                    /* no-op */
                }

                override fun onError(throwable: Throwable) {
                    /* no-op */
                }
            })
    }

    private fun mapperObs(data: String): ReactObservable<Int> {
        return ReactObservable.fromCallable(Callable<Int> {
            Thread.sleep(2000L)
            return@Callable data.toInt()
        })
    }
}