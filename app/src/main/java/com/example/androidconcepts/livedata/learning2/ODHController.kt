package com.example.androidconcepts.livedata.learning2

import android.widget.Toast

class ODHController constructor(
    private val odhUsecase: ODHUsecase
) {

    private val statusObserver = object : ObservableDataHolder.Observer<Int> {
        override fun onValueChanged(data: Int) {
            /* no-op */
        }
    }

    fun onClick() {
        odhUsecase.fetchData()
    }

    fun onStart() {
        odhUsecase.status.registerListener(statusObserver)
    }

    fun onStop() {
        odhUsecase.status.unregisterListener(statusObserver)
    }

}