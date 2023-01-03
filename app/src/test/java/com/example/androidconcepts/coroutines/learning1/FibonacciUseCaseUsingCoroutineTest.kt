package com.example.androidconcepts.coroutines.learning1

import kotlinx.coroutines.runBlocking
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test

class FibonacciUseCaseUsingCoroutineTest {

    private lateinit var SUT: FibonacciUseCaseUsingCoroutine

    @Test
    fun fibonacciNoOf10is55() = runBlocking {
        // act
        val result = SUT.getFibonacciNumber(10)

        // assert
        assert(result == 55)
    }

    @Before
    fun setUp() {
        SUT = FibonacciUseCaseUsingCoroutine()
    }
}