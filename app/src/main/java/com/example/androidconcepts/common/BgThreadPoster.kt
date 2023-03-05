package com.example.androidconcepts.common

import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.Future
import java.util.concurrent.SynchronousQueue
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit

open class BgThreadPoster {
    protected open val taskExecutor: ExecutorService = ThreadPoolExecutor(
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

class BgThreadPosterDualThreaded : BgThreadPoster() {
    // default override shows taskExecutor: ExecutorService get() = ...
    // it will return new executor each time. this mistake wasted 20mins :/
    override val taskExecutor: ExecutorService = Executors.newFixedThreadPool(2)
}

class BgThreadPosterSingleThreaded : BgThreadPoster() {
    // default override shows taskExecutor: ExecutorService get() = ...
    // it will return new executor each time. this mistake wasted 20mins :/
    override val taskExecutor: ExecutorService = Executors.newSingleThreadExecutor()
}