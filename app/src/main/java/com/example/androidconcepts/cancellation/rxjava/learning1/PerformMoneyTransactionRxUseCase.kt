package com.example.androidconcepts.cancellation.rxjava.learning1

import android.util.Log
import com.example.androidconcepts.cancellation.coroutines.learning1.blockingDelay
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers

class PerformMoneyTransactionRxUseCase {

    interface Listener {
        fun onSuccess(result: String)
        fun onError(reason: String)
    }

    private var disposable: Disposable? = null

    // Doubt : is the corresponding activity/fragment leaked? if yes till when? what about in case this is disposed?

    fun performMoneyTransactionAsync(callback: Listener) {
        disposable = Observable.just(1).map {

            val result = performMoneyTransferNetworkCalls()

            Log.d("debug", "network call result received : $result")

            saveResultInDb(result)

            return@map "transaction done successfully!"
        }.map {

            Log.d("debug", "second map result : $it")
            return@map it

        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(/* onNext */ { result ->

                Log.d("debug", "subscribe onNext : result $result")

                callback.onSuccess(result)

            }, /* onError */ { throwable ->
                Log.d("debug", "subscribe onError : reason ${throwable.message}")
                callback.onError(throwable.message ?: "some error occurred!")
            }, /* onComplete */ {
                Log.d("debug", "subscribe onComplete")
            })
    }

    private fun performMoneyTransferNetworkCalls(): String {
        repeat(5) {
            Log.d("debug", "repeat : $it isDisposed : ${disposable?.isDisposed}}")
            blockingDelay(1000)
        }

        return "Success"
    }

    private fun saveResultInDb(result: String) {
        Log.d("debug", " saving result started")
        blockingDelay(1000)
        Log.d("debug", " saved result")
    }

    fun cancelTransaction() {
        disposable?.dispose()
    }
}