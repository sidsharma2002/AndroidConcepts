package com.example.androidconcepts.cancellation.threads

import android.util.Log
import com.example.androidconcepts.cancellation.coroutines.learning1.blockingDelay

class ThreadInterruption1UseCase {

    private var thread: Thread? = null

    fun start() {
        thread = Thread {

            // even if thread is interrupted the runnable still completes unless
            // we use apis like wait() which throws InterruptedException explicitly.
            repeat(5) {
                Log.d("thread debug", "i = $it")
                blockingDelay(1000L)
            }

            // if the thread was interrupted earlier or while waiting, then an interrupted exception is thrown.
            synchronized(this) {
                (this as Object).wait()
            }
        }

        thread!!.start()
    }

    fun cancel() {
        thread?.interrupt()
    }
}