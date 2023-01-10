package com.example.androidconcepts.coroutines.learning1

import com.example.androidconcepts.common.BgThreadPoster
import com.example.androidconcepts.common.UiThreadPoster
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.concurrent.Executors
import java.util.concurrent.Future

class FibonacciUseCaseUsingCoroutine {
    suspend fun getFibonacciNumber(n: Int): Int = withContext(Dispatchers.Default) {
        return@withContext when (n) {
            0 -> 0
            1 -> 1
            else -> getFibonacciNumber(n - 1) + getFibonacciNumber(n - 2)
        }
    }
}

class FibonacciUseCaseUsingCallback constructor(
    private val bgThreadPoster: BgThreadPoster = BgThreadPoster(),
    private val uiThreadPoster: UiThreadPoster = UiThreadPoster()
) {

    interface Callback {
        fun onResult(fibonacciNumber: Int)
    }

    fun fetchFibonacciNumberAsync(n: Int, callback: Callback) = bgThreadPoster.post {
        val result = getFibonacciNumber(n)
        uiThreadPoster.post {
            callback.onResult(fibonacciNumber = result)
        }
    }

    private fun getFibonacciNumber(n: Int): Int {
        return when (n) {
            0 -> 0
            1 -> 1
            else -> getFibonacciNumber(n - 1) + getFibonacciNumber(n - 2)
        }
    }
}