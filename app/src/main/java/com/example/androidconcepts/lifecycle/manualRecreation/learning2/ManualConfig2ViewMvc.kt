package com.example.androidconcepts.lifecycle.manualRecreation.learning2

import android.view.View
import com.example.androidconcepts.common.Observable
import com.example.androidconcepts.lifecycle.manualRecreation.learning2.ManualConfig2ViewMvc.Listener

interface ManualConfig2ViewMvc: Observable<Listener> {
    interface Listener {
        // eg: on btn clicked
    }

    fun setupUi()
    fun bindData(count: Int)
    fun getRootView(): View
}

