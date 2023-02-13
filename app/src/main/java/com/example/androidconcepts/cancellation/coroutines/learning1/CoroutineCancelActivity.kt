package com.example.androidconcepts.cancellation.coroutines.learning1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.androidconcepts.R
import com.example.androidconcepts.cancellation.coroutines.learning1.answers.MoneyTransferAnswer1UseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.launch

class CoroutineCancelActivity : AppCompatActivity() {

    private val useCase = MoneyTransferAnswer1UseCase()
    private val scope = CoroutineScope(Dispatchers.Main.immediate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coroutine_cancel)

        scope.launch {
            val result = useCase.performMoneyTransaction() // useCase.performMoneyTransactionFixed()
            Log.d("debug1", "result from usecase : $result")
        }
    }

    override fun onPause() {
        super.onPause()
        scope.coroutineContext.cancelChildren()
    }
}