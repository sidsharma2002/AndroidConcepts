package com.example.androidconcepts.cancellation.coroutines.learning1

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.InputStream
import java.net.URL
import java.net.URLConnection

class PerformMoneyTransactionUseCase {

    suspend fun performMoneyTransaction(): String {
        val result = performMoneyTransferNetworkCalls()
        Log.d("debug", "network call result received : $result")
        saveResultInDb(result)
        return "transaction done successfully!"
    }

    private suspend fun performMoneyTransferNetworkCalls(): String = withContext(Dispatchers.IO) {
        repeat(5) {
            Log.d("debug", "$it")
            blockingDelay(1000)
        }

        Log.d("debug", "network calls done, returning result")
        return@withContext "done success"
    }

    private suspend fun saveResultInDb(result: String) = withContext(Dispatchers.IO) {
        Log.d("debug", " saving result started")
        blockingDelay(1000)
        Log.d("debug", " saved result started")
    }
}

fun blockingDelay(time: Long) {
    val finalTime = System.currentTimeMillis() + time
    var counter = 0;
    while (System.currentTimeMillis() < finalTime) {
        counter++;
    }
}