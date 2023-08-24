package com.example.androidconcepts.coroutines.performance

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ProcessImageUseCase {
    suspend fun process(time: Int) {
        // simulate algorithm invocation for time ms in a "blocking manner".
        // (fake load just to avoid elimination optimization by compilers)
        val finishTimeMillis = System.currentTimeMillis() + time
        var counter = 0
        while (System.currentTimeMillis() < finishTimeMillis) {
            counter++
        }
        if (counter > 250000000) {
            println("wow, your device has really fast cores")
        }
    }

    fun processBlocking(time: Int) {
        // simulate algorithm invocation for time ms in a "blocking manner".
        // (fake load just to avoid elimination optimization by compilers)
        val finishTimeMillis = System.currentTimeMillis() + time
        var counter = 0
        while (System.currentTimeMillis() < finishTimeMillis) {
            counter++
        }
        if (counter > 250000000) {
            println("wow, your device has really fast cores")
        }
    }
}
