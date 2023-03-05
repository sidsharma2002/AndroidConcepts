package com.example.androidconcepts.lifecycle.manualRecreation.learning2

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.androidconcepts.R
import com.example.androidconcepts.common.BgThreadPoster
import com.example.androidconcepts.common.UiThreadPoster
import com.example.androidconcepts.coroutines.learning1.FibonacciUseCaseUsingCallback
import com.example.androidconcepts.jcip.common.DelayedCallbackUseCase
import com.example.androidconcepts.lifecycle.common.LifecycleLoggerFragment
import com.example.androidconcepts.lifecycle.common.reattachFragForConfigChanges

class ManualConfig2Fragment : LifecycleLoggerFragment() {

    override val TAG: String
        get() = "config $counter"

    private var isInLandscape: Boolean = false
    private var viewMvc: ManualConfig2ViewMvc? = null

    private val delayedCallbackUseCase = DelayedCallbackUseCase(BgThreadPoster(), UiThreadPoster())
    private val delayedDataCallback = DelayedCallbackUseCase.Listener { counter ->
        viewMvc!!.bindData(counter)
    }

    private val randomNumber = (0..10).random()
    var counter = 1

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)

        isInLandscape =
            requireContext().resources?.configuration?.orientation == Configuration.ORIENTATION_LANDSCAPE

        viewMvc = if (isInLandscape) {
            ManualConfig2ViewMvcLandscapeImpl(inflater, counter, randomNumber)
        } else {
            ManualConfig2ViewMvcPortraitImpl(inflater, counter, randomNumber)
        }

        return viewMvc!!.getRootView()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewMvc!!.setupUi()
    }

    override fun onStart() {
        super.onStart()
        delayedCallbackUseCase.registerListener(delayedDataCallback)
        delayedCallbackUseCase.fetchAsync(3000L)
    }

    override fun onStop() {
        super.onStop()
        delayedCallbackUseCase.unregisterListener(delayedDataCallback)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewMvc = null // help gc
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        Log.d(TAG, "onConfigChanged $counter")

        reattachFragForConfigChanges(newConfig, superOnConfigChange = {
            super.onConfigurationChanged(newConfig)
        })
    }
}