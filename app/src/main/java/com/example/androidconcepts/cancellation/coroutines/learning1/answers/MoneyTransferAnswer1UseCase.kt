package com.example.androidconcepts.cancellation.coroutines.learning1.answers

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.isActive
import kotlinx.coroutines.withContext
import kotlin.coroutines.coroutineContext

class MoneyTransferAnswer1UseCase {
    suspend fun performMoneyTransaction(): String {
        val result = performMoneyTransferNetworkCalls()
        Log.d("debug", "network call result received : $result")
        Log.d("debug", "isActive : ${coroutineContext.isActive}")
        saveResultInDb(result)
        Log.d("debug", "saved in db line 12")
        return "transaction done successfully!"
    }

    private suspend fun performMoneyTransferNetworkCalls(): String {
        return try {
            repeat(5) {
                Log.d("debug", "$it isActive : ${coroutineContext.isActive}")
                blockingDelay(1000)
            }
            Log.d("debug", "network calls done, returning result")
            "done success"
        } catch (e: Exception) { // CancellationException is not thrown here hence not caught!
            Log.d("debug_error", "" + e.message)
            e.printStackTrace()
            "done failure!"
        }
    }

    private suspend fun saveResultInDb(result: String) = withContext(Dispatchers.IO) {
        Log.d("debug", " saving result started")
        Log.d("debug", "isActive : ${kotlin.coroutines.coroutineContext.isActive}")
        blockingDelay(2000)
        Log.d("debug", "isActive : ${kotlin.coroutines.coroutineContext.isActive}")
        Log.d("debug", " saved result started")
    }

    suspend fun performMoneyTransactionFixed(): String = withContext(Dispatchers.IO) {
        val result = performMoneyTransferNetworkCalls()
        Log.d("debug", "network call result received : $result")
        Log.d("debug", "isActive : ${coroutineContext.isActive}")
        saveResultInDb(result)
        Log.d("debug", "saved in db line 12")
        return@withContext "transaction done successfully!"
    }

    private fun blockingDelay(time: Long) {
        val finalTime = System.currentTimeMillis() + time
        var counter = 0;
        while (System.currentTimeMillis() < finalTime) {
            counter++;
        }
    }
}