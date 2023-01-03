package com.example.androidconcepts

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.androidconcepts.others.common.UiThreadPoster
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // TODO : move to coroutines package
        CoroutineScope(Job() + Dispatchers.Main.immediate).launch {
            suspend {   // doesn't block ui
                performWhileTrueLoop()
            }

            performWhileTrueLoop() // blocks ui
        }
    }

    private suspend fun showToastEvery3Sec() {
        while (true) {
            delay(3 * 1000)
            UiThreadPoster().post {
                Toast.makeText(this.applicationContext, "yeah", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private suspend fun suspendWrapper() {
        performWhileTrueLoop()
    }

    private suspend fun performWhileTrueLoop() = withContext(Dispatchers.Main.immediate){
        while (true) {

        }
    }
}