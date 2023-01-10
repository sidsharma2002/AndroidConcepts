package com.example.androidconcepts.coroutines.learning1

import android.os.Handler
import com.example.androidconcepts.common.UiThreadPoster
import com.example.androidconcepts.common.UiThreadPosterTD
import io.mockk.mockk
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

class FibonacciUseCaseCallbackTest {

    private lateinit var SUT: FibonacciUseCaseUsingCallback

    @Test
    @Throws(InterruptedException::class)
    fun fibonacciNoOf10is55() {
        // arrange
        val countDownLatch = CountDownLatch(1)
        var result: Int = -1

        // act
        SUT.fetchFibonacciNumberAsync(10, object : FibonacciUseCaseUsingCallback.Callback {
            override fun onResult(fibonacciNumber: Int) {
                result = fibonacciNumber
                countDownLatch.countDown()
            }
        })

        // assert
        countDownLatch.await(1, TimeUnit.SECONDS)
        assert(result == 55)
    }

    @Before
    fun setup() {
        SUT = FibonacciUseCaseUsingCallback(uiThreadPoster = UiThreadPosterTD())
    }
}