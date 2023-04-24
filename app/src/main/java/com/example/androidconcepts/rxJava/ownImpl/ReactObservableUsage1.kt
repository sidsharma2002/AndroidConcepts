package com.example.androidconcepts.rxJava.ownImpl

import com.example.androidconcepts.cancellation.coroutines.learning1.blockingDelay
import java.util.concurrent.Callable

class ReactObservableUsage1 {

    fun fetchOBS(): ReactObservable<String> {
        return ReactObservable.fromCallable(Callable<String> {
            blockingDelay(1000L)
            return@Callable "1"
        })
    }

    fun mapToIntOBS(data: String): ReactObservable<Int> {
        return ReactObservable.fromCallable(Callable<Int> {
            blockingDelay(1000L)
            return@Callable data.toInt()
        })
    }
}