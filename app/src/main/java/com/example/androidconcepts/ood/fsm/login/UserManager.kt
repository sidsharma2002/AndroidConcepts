package com.example.androidconcepts.ood.fsm.login

import com.example.androidconcepts.common.BaseObservable

class UserManager: BaseObservable<UserManager.Listener>() {

    interface Listener {
        fun onLoginSuccess()
        fun onLoginFailure()
    }

    fun login() {
        /* no-op */
    }
}