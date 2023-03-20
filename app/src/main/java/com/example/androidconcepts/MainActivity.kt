package com.example.androidconcepts

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.androidconcepts.common.UiThreadPoster
import com.example.androidconcepts.fileStorage.learning1.FileLearning1
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        FileLearning1(this).start()
    }
}