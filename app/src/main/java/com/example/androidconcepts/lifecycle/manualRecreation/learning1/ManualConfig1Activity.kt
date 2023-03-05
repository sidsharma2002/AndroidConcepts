package com.example.androidconcepts.lifecycle.manualRecreation.learning1

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.example.androidconcepts.R

class ManualConfig1Activity : AppCompatActivity() {

    private val TAG = "config ManualConfig1Act"
    private var textView: TextView? = null

    private val randomNumber = (0..10).random()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate")
        setContentViewAndViewReferences()

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container_view_tag, ManualConfig1Fragment())
            .commit()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)

        Log.d(TAG, "onConfigChanged")
        Log.d(TAG, "random after config change : $randomNumber") // remains same.

        setContentViewAndViewReferences()
    }

    private fun setContentViewAndViewReferences() {
        setContentView(R.layout.activity_manual_config1) // automatically sets layout for portrait/landscape.
        textView = findViewById(R.id.tv1)
        textView!!.text = "randomNo : $randomNumber"
    }
}