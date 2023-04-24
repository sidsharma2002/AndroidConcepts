package com.example.androidconcepts.livedata.learning1.before

import com.example.androidconcepts.common.BaseObservable
import com.example.androidconcepts.common.BgThreadPoster
import com.example.androidconcepts.common.UiThreadPoster
import com.example.androidconcepts.livedata.learning1.LiveDataApiEndpointSync

class LiveDataLearning1BeforeUseCase constructor(
    private val apiEndPointSync: LiveDataApiEndpointSync,
    private val uiThreadPoster: UiThreadPoster = UiThreadPoster(),
    private val bgThreadPoster: BgThreadPoster = BgThreadPoster()
) : BaseObservable<LiveDataLearning1BeforeUseCase.Listener>() {

    interface Listener {
        fun onNumberCached(number: Int)
    }

    private var cachedNumber: Int? = null  // apart from notifying the cached value to the listeners, we are also storing it.
    private val lock: Any = Any()

    fun fetchDataFromServerAsync() = bgThreadPoster.post {
        val result = apiEndPointSync.fetchDataFromServer()
        setValueAndNotifyListeners(result)
    }

    fun fetchDataFromLocalDbAsync() = bgThreadPoster.post {
        val result = apiEndPointSync.fetchDataFromServer()
        setValueAndNotifyListeners(result)
    }

    private fun setValueAndNotifyListeners(value: Int) {
        synchronized(lock) {
            cachedNumber = value
            notifyListeners()
        }
    }

    private fun notifyListeners() {
        uiThreadPoster.post {
            for (listener in getAllListeners()) {
                listener.onNumberCached(cachedNumber!!)
            }
        }
    }

    fun getCachedNumberValue(): Int? {
        synchronized(lock) {
            return cachedNumber
        }
    }
}