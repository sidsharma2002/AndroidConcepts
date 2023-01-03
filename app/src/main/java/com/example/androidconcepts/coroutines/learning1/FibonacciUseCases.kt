package com.example.androidconcepts.coroutines.learning1

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

class FibonacciUseCaseUsingCallback {

    interface Callback {
        fun onResult(fibonacciNumber: Int)
    }

    private val taskPoolExecutor = Executors.newFixedThreadPool(3)

    fun fetchFibonacciNumberAsync(n: Int, callback: Callback): Future<*> = taskPoolExecutor.submit {
        val result = getFibonacciNumber(n)
        callback.onResult(fibonacciNumber = result)
    }

    private fun getFibonacciNumber(n: Int): Int {
        return when (n) {
            0 -> 0
            1 -> 1
            else -> getFibonacciNumber(n - 1) + getFibonacciNumber(n - 2)
        }
    }
}