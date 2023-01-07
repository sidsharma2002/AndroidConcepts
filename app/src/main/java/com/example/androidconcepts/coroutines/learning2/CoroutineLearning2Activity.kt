package com.example.androidconcepts.coroutines.learning2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.androidconcepts.R
import kotlinx.coroutines.*

class CoroutineLearning2Activity : AppCompatActivity() {

    private val whileTrueLoopRunner: WhileTrueLoopRunner = WhileTrueLoopRunner()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coroutine_learning2)

        CoroutineScope(SupervisorJob()).launch {
            withContext(Dispatchers.Main.immediate) {
                whileTrueLoopRunner.runWhileTrueLoop()
            }
        }
    }
}