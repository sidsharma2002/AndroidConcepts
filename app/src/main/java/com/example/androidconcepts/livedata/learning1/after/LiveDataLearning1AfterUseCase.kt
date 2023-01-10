package com.example.androidconcepts.livedata.learning1.after

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.androidconcepts.common.BgThreadPoster
import com.example.androidconcepts.common.UiThreadPoster
import com.example.androidconcepts.livedata.learning1.LiveDataApiEndpointSync

class LiveDataLearning1AfterUseCase constructor(
    private val apiEndPointSync: LiveDataApiEndpointSync,
    private val bgThreadPoster: BgThreadPoster = BgThreadPoster()
) {
    private val _cachedNumber = MutableLiveData<Int>()
    val cachedNumber: LiveData<Int> = _cachedNumber

    fun fetchDataFromServerAsync() = bgThreadPoster.post {
        val result = apiEndPointSync.fetchDataFromServer()
        _cachedNumber.postValue(result)
    }

    fun fetchDataFromLocalDbAsync() = bgThreadPoster.post {
        val result = apiEndPointSync.fetchDataFromServer()
        _cachedNumber.postValue(result)
    }
}