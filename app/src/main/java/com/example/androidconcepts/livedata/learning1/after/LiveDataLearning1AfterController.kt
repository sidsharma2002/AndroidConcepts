package com.example.androidconcepts.livedata.learning1.after

import androidx.lifecycle.LifecycleOwner

class LiveDataLearning1AfterController constructor(
    private val useCase: LiveDataLearning1AfterUseCase,
    private val lifecycleOwner: LifecycleOwner
) {

    fun onStart() {
        subscribeToObservers()
        useCase.fetchDataFromServerAsync()
        useCase.fetchDataFromLocalDbAsync()
    }

    private fun subscribeToObservers() {
        useCase.cachedNumber.observe(lifecycleOwner) {
            /* no-op */
        }
    }

    fun onClick() {
        val cachedNumberValue = useCase.cachedNumber.value
    }
}