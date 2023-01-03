package com.example.androidconcepts.coroutines.learning1

import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class FibonacciUseCaseCallbackTest {

    private lateinit var SUT: FibonacciUseCaseUsingCallback
    private var result : Int = 0

    @Test
    fun fibonacciNoOf10is55() {
        // act
        val future = SUT.fetchFibonacciNumberAsync(10, object : FibonacciUseCaseUsingCallback.Callback {
            override fun onResult(fibonacciNumber: Int) {
                result = fibonacciNumber
            }
        })

        future.get()

        // assert
        assert(result == 55)
    }

    @Before
    fun setup() {
        SUT = FibonacciUseCaseUsingCallback()
    }
}