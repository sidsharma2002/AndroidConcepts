package com.example.androidconcepts.lifecycle.manualRecreation.learning1

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import com.example.androidconcepts.R
import com.example.androidconcepts.lifecycle.common.LifecycleLoggerFragment

class ManualConfig1Fragment : LifecycleLoggerFragment() {

    override val TAG: String
        get() = "config fragment1"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        // automatically sets layout for portrait/landscape.
        return layoutInflater.inflate(
            /* resource = */ R.layout.fragment_manual_config_1,
            /* root = */ null
        )
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        Log.d(TAG, "onConfigChanged")

        // means detach from ui (onDestroyView) not onDetach of lifecycle.
        requireActivity().supportFragmentManager.beginTransaction().detach(this)
            .commitAllowingStateLoss()

        // first onConfigChanged of fragment is detected then of activity.
        super.onConfigurationChanged(newConfig)

        // means attach to ui (onCreateView) not onAttach of lifecycle.
        // NOTE : fragment container with same id should be present in corresponding landscape ui.
        requireActivity().supportFragmentManager.beginTransaction().attach(this)
            .commitAllowingStateLoss()
    }
}