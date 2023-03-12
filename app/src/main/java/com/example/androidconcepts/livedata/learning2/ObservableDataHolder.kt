package com.example.androidconcepts.livedata.learning2

import androidx.annotation.GuardedBy
import com.example.androidconcepts.common.EmitOnRegisterObservable
import com.example.androidconcepts.common.UiThreadPoster


/**
 * An observable data holder used in cases where we want to get a notification of the data when a observer is registered.
 * Typically used in cases where important notifications can miss due to unregistration of observers in onStop().
 *
 * Simple to use as registration doesn't require any viewLifecycleOwner hence it can be used for all purposes and not only as a UI-DataHolder.
 * Also we don't need to think about which one to use i.e setValue or postValue.
 *
 * @author Siddharth Sharma
 */
@Suppress("UNCHECKED_CAST")
class ObservableDataHolder<T> constructor(
    initialValue: T? = null,
    private val uiThreadPoster: UiThreadPoster = UiThreadPoster()
) : EmitOnRegisterObservable<ObservableDataHolder.Observer<T>>(uiThreadPoster) {

    interface Observer<T> {
        fun onValueChanged(data: T)
    }

    @GuardedBy("LOCK")
    private var data: Object

    private val NOT_SET = Object()

    init {
        if (initialValue == null)
            data = NOT_SET
        else
            data = initialValue as Object
    }

    private val LOCK = Object()

    /**
     * NOTE : On main thread, observers are notified via mainLooper.post api rather than notifying immediately.
     * TODO : what are the repercussions for this?
     */
    fun setData(newData: T) {
        synchronized(LOCK) {
            this.data = newData as Object

            uiThreadPoster.post {
                notifyAllListeners(newData)
            }
        }
    }

    fun getValue(): T? {
        synchronized(LOCK) {
            if (isNotSet())
                return null
            else
                return data as T
        }
    }

    fun isNotSet(): Boolean {
        return data == NOT_SET
    }

    private fun notifyAllListeners(newData: T) {
        for (listener in getAllListeners())
            listener.onValueChanged(newData)
    }

    override fun notifyObserverOnRegistration(listener: Observer<T>) {
        if (isNotSet()) return

        uiThreadPoster.post {
            listener.onValueChanged(data as T)
        }
    }
}