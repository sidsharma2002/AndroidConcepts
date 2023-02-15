package com.example.androidconcepts.cancellation.rxjava.learning1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.androidconcepts.R

class RxCancelActivity : AppCompatActivity() {
    private val usecase = PerformMoneyTransactionRxUseCase()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rx_cancel)

        usecase.performMoneyTransactionAsync(object: PerformMoneyTransactionRxUseCase.Listener {
            override fun onSuccess(result: String) {
                Log.d("debug", "activity : onSuccess : $result")
            }

            override fun onError(reason: String) {
                Log.d("debug", "activity : onError : $reason")
            }
        })
    }

    override fun onPause() {
        super.onPause()
        usecase.cancelTransaction()
    }
}