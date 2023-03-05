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

    // ui references
    private var tvHeadline: TextView? = null
    private var tvBody: TextView? = null

    private var isInLandscape: Boolean = false

    private val randomNumber = (0..10).random()
    var counter = 1

    private val delayedCallbackUseCase = DelayedCallbackUseCase(BgThreadPoster(), UiThreadPoster())

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        isInLandscape =
            requireContext().resources?.configuration?.orientation == Configuration.ORIENTATION_LANDSCAPE

        return layoutInflater.inflate(R.layout.fragment_manual_config_2, null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (isInLandscape)
            setupUiForLandscape(view)
        else
            setupUiForPortrait(view)

        fetchFibonacciNumbers()
    }

    private fun fetchFibonacciNumbers() {
        delayedCallbackUseCase.fetchAsync(3000L) {
            tvHeadline?.text = "headline fetched"

            // for landscape
            tvBody?.text = "body fetched"
        }
    }

    private fun setupUiForPortrait(view: View) {
        tvHeadline = view.findViewById(R.id.tv3)
        tvHeadline!!.text = "headline counter : $counter no : $randomNumber"
    }

    private fun setupUiForLandscape(view: View) {
        tvHeadline = view.findViewById(R.id.tv3)
        tvHeadline!!.text = "headline counter : $counter no : $randomNumber"

        tvBody = view.findViewById(R.id.tv4)
        tvBody!!.text = "body counter"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        tvBody = null
        tvHeadline = null
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        Log.d(TAG, "onConfigChanged $counter")

        reattachFragForConfigChanges(newConfig, superOnConfigChange = {
            super.onConfigurationChanged(newConfig)
        })
    }
}