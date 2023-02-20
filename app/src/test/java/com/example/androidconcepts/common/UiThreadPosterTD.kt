package com.example.androidconcepts.common

import android.os.Handler
import java.util.concurrent.CountDownLatch
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

class UiThreadPosterTD : UiThreadPoster() {

    private val taskExecutor = Executors.newSingleThreadExecutor()

    override fun getUiHandler(): Handler? {
        return null
    }

    override fun post(runnable: Runnable) {
        taskExecutor.submit(runnable)
    }

    override fun postDelayed(delayTime: Long, runnable: Runnable) {
        taskExecutor.submit {
            Thread.sleep(delayTime)
            runnable.run()
        }
    }

    /**
     * NOTE : can cause deadlock if submitted to "this".
     * eg :  uiThreadPoster.post {
     *          uiThreadPoster.postDelayedAndWait(...)
     *      }
     */
    override fun postDelayedAndWait(delayTime: Long, awaitTime: Long, runnable: Runnable) {
        val latch = CountDownLatch(1)

        taskExecutor.submit {
            Thread.sleep(delayTime)
            runnable.run()
            latch.countDown()
        }

        latch.await(awaitTime, TimeUnit.SECONDS)
    }
}