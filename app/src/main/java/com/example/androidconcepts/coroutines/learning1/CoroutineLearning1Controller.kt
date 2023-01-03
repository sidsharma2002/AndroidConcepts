package com.example.androidconcepts.coroutines.learning1

import com.example.androidconcepts.coroutines.learning1.FibonacciUseCaseUsingCallback.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class CoroutineLearning1Controller constructor(
    private val fibonacciUseCaseUsingCoroutine: FibonacciUseCaseUsingCoroutine,
    private val fibonacciUseCaseUsingCallback: FibonacciUseCaseUsingCallback
) {

    private val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Default)

    fun fetchFibonacciNumber() {
        // usage of coroutines
        coroutineScope.launch {
            val result = fibonacciUseCaseUsingCoroutine.getFibonacciNumber(10)
        }

        // usage of callback
        fibonacciUseCaseUsingCallback.fetchFibonacciNumberAsync(10, object : Callback {
            override fun onResult(fibonacciNumber: Int) {
                val result = fibonacciNumber
            }
        })
    }
}