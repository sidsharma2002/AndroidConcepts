package com.example.androidconcepts.common

import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

class BgThreadPosterTest {

    private lateinit var SUT: BgThreadPoster
    private var mainThreadName: String = ""

    @Before
    fun setup() {
        SUT = BgThreadPoster()
        mainThreadName = Thread.currentThread().name
    }

    @Test
    @Throws(InterruptedException::class)
    fun postedRunnableExecutesSuccessfullyOnBackgroundThread() {
        // arrange
        var ran = false
        var isBgThread = true
        val countDownLatch = CountDownLatch(1)

        val runnable = Runnable {
            ran = true
            isBgThread = !(Thread.currentThread().name.equals(mainThreadName))
            countDownLatch.countDown()
        }

        // act
        SUT.post(runnable)

        // assert
        countDownLatch.await(1, TimeUnit.SECONDS)
        assert(ran)
        assert(isBgThread)
    }
}