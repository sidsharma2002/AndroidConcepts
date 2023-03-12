package com.example.androidconcepts.common

import android.os.Looper
import androidx.annotation.MainThread

abstract class EmitOnRegisterObservable<ListenerClass> constructor(
    private val uiThreadPoster: UiThreadPoster
) : BaseObservable<ListenerClass>() {

    override fun registerListener(listener: ListenerClass) {
        super.registerListener(listener)

        if (isMainThread()) {
            notifyObserverWhenRegistered(listener)
        } else {
            uiThreadPoster.post {
                notifyObserverWhenRegistered(listener)
            }
        }
    }

    protected fun isMainThread(): Boolean {
        return Thread.currentThread().id == 1L
    }

    abstract fun notifyObserverWhenRegistered(listener: ListenerClass)
}