package com.example.androidconcepts.coroutines.performance

class ProcessImageUseCase {
    suspend fun process() {
        // simulate algorithm invocation for 100 ms
        // (fake load just to avoid elimination optimization by compilers)
        val finishTimeMillis = System.currentTimeMillis() + 100
        var counter = 0
        while (System.currentTimeMillis() < finishTimeMillis) {
            counter++
        }
        if (counter > 250000000) {
            println("wow, your device has really fast cores")
        }
    }
}
