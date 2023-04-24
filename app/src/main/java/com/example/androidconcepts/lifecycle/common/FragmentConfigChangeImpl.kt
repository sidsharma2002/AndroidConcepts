package com.example.androidconcepts.lifecycle.common

import android.content.res.Configuration
import androidx.fragment.app.Fragment

fun Fragment.reattachFragForConfigChanges(newConfig: Configuration, superOnConfigChange: () -> Unit) {
    // means detach from ui (onDestroyView) not onDetach of lifecycle.
    requireActivity().supportFragmentManager.beginTransaction().detach(this)
        .commitAllowingStateLoss()

    // first onConfigChanged of fragment is detected then of activity.
    superOnConfigChange.invoke()

    // means attach to ui (onCreateView) not onAttach of lifecycle.
    // NOTE : fragment container with same id should be present in corresponding landscape ui.
    requireActivity().supportFragmentManager.beginTransaction().attach(this)
        .commitAllowingStateLoss()
}