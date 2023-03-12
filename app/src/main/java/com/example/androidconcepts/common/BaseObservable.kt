package com.example.androidconcepts.common

import java.util.*

interface Observable<ListenerClass> {
    fun registerListener(listener: ListenerClass)
    fun unregisterListener(listener: ListenerClass)
}

open class BaseObservable<ListenerClass> : Observable<ListenerClass> {

    private val listeners: MutableList<ListenerClass> by lazy {
        Collections.synchronizedList(mutableListOf())
    }

    override fun registerListener(listener: ListenerClass) {
        this.listeners.add(listener)
    }

    override fun unregisterListener(listener: ListenerClass) {
        this.listeners.remove(listener)
    }

    protected fun getAllListeners(): List<ListenerClass> {
        return listeners
    }
}