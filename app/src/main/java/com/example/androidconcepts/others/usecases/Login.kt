package com.example.androidconcepts.others.usecases

import com.example.androidconcepts.others.common.BaseObservable
import com.example.androidconcepts.others.common.UiThreadPoster

class LoginUseCase constructor(
    private val uiThreadPoster: UiThreadPoster
) : BaseObservable<LoginUseCase.LoginListener>() {

    interface LoginListener {
        fun onLoggedIn()
    }

    fun startLogin() {
        notifyListenersAboutLogIn()
    }

    private fun notifyListenersAboutLogIn() {
        uiThreadPoster.post {
            getAllListeners().forEach {
                it.onLoggedIn()
            }
        }
    }

}