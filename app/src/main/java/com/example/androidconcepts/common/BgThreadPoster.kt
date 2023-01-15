package com.example.androidconcepts.common

import java.util.concurrent.Future
import java.util.concurrent.SynchronousQueue
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit

class BgThreadPoster {
    private val taskExecutor: ThreadPoolExecutor = ThreadPoolExecutor(
        /* corePoolSize = */ 3,
        /* maximumPoolSize = */ Int.MAX_VALUE,
        /* keepAliveTime = */ 60,
        /* unit = */ TimeUnit.SECONDS,
        /* workQueue = */ SynchronousQueue()
    )

    fun post(runnable: Runnable): Future<*> {
        return taskExecutor.submit(runnable)
    }
}