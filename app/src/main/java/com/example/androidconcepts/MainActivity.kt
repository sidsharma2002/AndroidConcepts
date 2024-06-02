package com.example.androidconcepts

import android.os.Bundle
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.androidconcepts.coroutines.learning3.IOCallsOnDifferentDispatcher1

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.btn).setOnClickListener {

            val etIterations = findViewById<EditText>(R.id.et_iterations)
            val etThreads = findViewById<EditText>(R.id.et_threads)

            IOCallsOnDifferentDispatcher1().startDownloading(
                context = this,
                iterations = etIterations.text.toString().toInt(),
                noOfThreads = etThreads.text.toString().toInt()
            )
        }

        // 50, 5, 4215

        // AndroidThreadSpammer().execute()

    }
}