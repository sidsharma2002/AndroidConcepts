package com.example.androidconcepts.common

import java.util.*

open class BaseObservable<ListenerClass> {

    private val listeners: MutableList<ListenerClass> by lazy {
        Collections.synchronizedList(listOf())
    }

    fun registerListener(listener: ListenerClass) {
        this.listeners.add(listener)
    }

    fun unregisterListener(listener: ListenerClass) {
        this.listeners.remove(listener)
    }

    protected fun getAllListeners(): List<ListenerClass> {
        return listeners
    }
}