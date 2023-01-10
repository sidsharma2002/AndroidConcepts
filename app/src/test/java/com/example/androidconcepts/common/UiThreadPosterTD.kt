package com.example.androidconcepts.common

import android.os.Handler
import java.util.concurrent.Executors

class UiThreadPosterTD : UiThreadPoster() {

    private val taskExecutor = Executors.newSingleThreadExecutor()

    override fun getUiHandler(): Handler? {
        return null
    }

    override fun post(runnable: Runnable) {
        taskExecutor.submit(runnable)
    }
}