package com.example.androidconcepts.ood.fsm.login

import com.example.androidconcepts.common.BaseObservable
import com.example.androidconcepts.common.Observable

interface LoginViewMvc: Observable<LoginViewMvc.Listener> {
    interface Listener {
        fun onWhatsAppLoginBtnClicked()

        fun onGoogleLoginBtnClicked()

        fun onPhoneNoInputClicked()
        fun onPhoneNoInputEntered()
        fun onPhoneNoLoginBtnClicked()
    }

    fun showProgress()
    fun hideProgress()
}