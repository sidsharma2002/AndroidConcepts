package com.example.androidconcepts.common

import android.os.Handler
import android.os.Looper

open class UiThreadPoster {
    private val handler: Handler? = getUiHandler() // returns null only in case of test double

    protected open fun getUiHandler(): Handler? {
        return Handler(Looper.getMainLooper())
    }

    open fun post(runnable: Runnable) {
        handler!!.post(runnable)
    }

    open fun postDelayed(delayTime: Long, runnable: Runnable) {
        handler!!.postDelayed(runnable, delayTime)
    }
}