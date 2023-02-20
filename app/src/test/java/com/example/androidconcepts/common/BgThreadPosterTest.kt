package com.example.androidconcepts.common

import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import java.util.concurrent.CountDownLatch
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.Future
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

    @Test
    fun postingNRunnablesCreateNThreads() {
        // arrange
        val jobs = mutableListOf<Future<*>>()

        // act
        repeat(20) {
            jobs += SUT.post {
                Thread.sleep(500L)
                println("i : $it + thread name : " + Thread.currentThread().name)
            }
        }

        // assert
        jobs.forEach {
            it.get()
        }
    }

    @Test
    fun deadlockOnSingleThreadedExecutorTest() {
        // arrange
        val SUT1 = BgThreadPosterSingleThreaded()

        // act
        SUT1.post {
            println("posted runnable1 started " + Thread.currentThread().name)

            SUT1.post {
                println("posted runnable2 " + Thread.currentThread().name)
            }.get() // deadlock

            println("posted runnable1 ended " + Thread.currentThread().name)
        }.get()
    }

    @Test
    fun deadlockOnDualThreadedExecutorTest() {
        // arrange
        val SUT1 = BgThreadPosterDualThreaded()

        // act
        SUT1.post {
            println("posted runnable1 started " + Thread.currentThread().name) // thread-1

            SUT1.post {
                println("posted runnable2 started " + Thread.currentThread().name) // thread2

                SUT1.post {
                    println("posted runnable3 started " + Thread.currentThread().name)
                }.get() // deadlocked at this as thread1 and thread2 are occupied and blocked.

                println("posted runnable2 ended " + Thread.currentThread().name)
            }.get()

            println("posted runnable1 ended " + Thread.currentThread().name)
        }.get()

        // Explanation : here t2 waits for runnable3 to complete on t1 but t1 waits for runnable2 to complete on t2.
        // see figure ->
        // t1 : [runnable1 = line89, [post runnable2], [get() on runnable2], line107] , [runnable3]
        // t2 : [runnable2 = line92, [post runnable3], [get() on runnable3], line104]
    }
}