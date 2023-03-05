package com.example.androidconcepts.lifecycle.manualRecreation.learning2

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.androidconcepts.R

class ManualConfigActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentViewAndReferences()

        repeat(4) {
            val frag = ManualConfig2Fragment()
            frag.counter = it
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container_view_tag, frag, null)
                .addToBackStack(null)
                .commit()
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        setContentViewAndReferences()
    }

    private fun setContentViewAndReferences() {
        setContentView(R.layout.activity_manual_config2)
    }
}