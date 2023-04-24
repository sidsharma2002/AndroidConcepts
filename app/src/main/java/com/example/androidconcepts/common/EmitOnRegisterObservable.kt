package com.example.androidconcepts.common

abstract class EmitOnRegisterObservable<ListenerClass> constructor(
    private val uiThreadPoster: UiThreadPoster
) : BaseObservable<ListenerClass>() {

    override fun registerListener(listener: ListenerClass) {
        super.registerListener(listener)
        notifyObserverOnRegistration(listener)
    }

    private fun isMainThread(): Boolean {
        return Thread.currentThread().id == 1L
    }

    abstract fun notifyObserverOnRegistration(listener: ListenerClass)
}