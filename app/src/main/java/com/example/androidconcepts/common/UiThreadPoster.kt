package com.example.androidconcepts.common

import android.os.Handler
import android.os.Looper

class UiThreadPoster constructor(
    private val handler: Handler = Handler(Looper.getMainLooper())
) {

    fun post(runnable: Runnable) {
        handler.post(runnable)
    }

    fun postDelayed(delayTime: Long, runnable: Runnable) {
        handler.postDelayed(runnable, delayTime)
    }
}