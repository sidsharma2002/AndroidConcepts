package com.example.androidconcepts.lifecycle.manualRecreation.learning2

import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.example.androidconcepts.R
import com.example.androidconcepts.common.BaseObservable

class ManualConfig2ViewMvcPortraitImpl constructor(
    layoutInflater: LayoutInflater,
    private val counter: Int,
    private val randomNo: Int
): ManualConfig2ViewMvc, BaseObservable<ManualConfig2ViewMvc.Listener>() {

    private var rootView: View = layoutInflater.inflate(R.layout.fragment_manual_config_2, null)
    private var tvHeading: TextView = rootView.findViewById(R.id.tv3)

    override fun setupUi() {
        tvHeading.text = "headline counter : $counter no : $randomNo"
    }

    override fun bindData(counter: Int) {
        tvHeading.text = "PORTRAIT ViewMVC : data fetched $counter"
    }

    override fun getRootView(): View {
        return rootView
    }
}