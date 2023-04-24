package com.example.androidconcepts.livedata.learning2

import com.example.androidconcepts.common.BgThreadPoster

class ODHUsecase constructor(
    private val bgThreadPoster: BgThreadPoster = BgThreadPoster()
) {
    val status = ObservableDataHolder<Int>()

    fun fetchData() {
        bgThreadPoster.post {
            Thread.sleep(3000L)
            status.setData(10)  // no need to think about postValue/setValue
        }
    }
}