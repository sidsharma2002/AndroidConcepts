package com.example.androidconcepts.livedata.learning1.before

class LiveDataLearning1BeforeController constructor(
    private val useCase: LiveDataLearning1BeforeUseCase
) {
    private lateinit var useCaseListener: LiveDataLearning1BeforeUseCase.Listener

    fun onStart() {
        subscribeToObservers()
        useCase.fetchDataFromServerAsync()
        useCase.fetchDataFromLocalDbAsync()
    }

    private fun subscribeToObservers() {
        if (::useCaseListener.isInitialized.not()) {
            initUseCaseListener()
        }

        useCase.registerListener(useCaseListener)
    }

    private fun initUseCaseListener() {
        useCaseListener = object : LiveDataLearning1BeforeUseCase.Listener {
            override fun onNumberCached(number: Int) {
                /* no-op */
            }
        }
    }

    fun onClick() {
        val cachedNumberValue = useCase.getCachedNumberValue()
    }

    fun onPause() {
        useCase.unregisterListener(useCaseListener)
    }
}