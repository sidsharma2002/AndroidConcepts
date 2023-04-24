package com.example.androidconcepts.ood.fsm.login

class LoginScreenController constructor(
    private val userManager: UserManager
) {

    private lateinit var viewMvc: LoginViewMvc

    fun onStart() {
        viewMvc.registerListener(viewListener)
        userManager.registerListener(userManagerListener)
    }

    sealed class LoginState {
        sealed class WhatsAppLogin : LoginState() {
            object PROGRESS : WhatsAppLogin()
            object SUCCESS : WhatsAppLogin()
            object FAILURE : WhatsAppLogin()
        }

        sealed class OtpLogin : LoginState() {
            object PROGRESS : OtpLogin()
            object SUCCESS : OtpLogin()
            object FAILURE : OtpLogin()
        }

        sealed class GoogleLogin : LoginState() {
            object PROGRESS : GoogleLogin()
            object SUCCESS : GoogleLogin()
            object FAILURE : GoogleLogin()
        }
    }

    fun setStateAndHandle(newState: LoginState) {
        when (newState) {
            LoginState.WhatsAppLogin.SUCCESS -> {
                viewMvc.hideProgress()
            }

            LoginState.WhatsAppLogin.FAILURE -> {
                viewMvc.hideProgress()
            }

            LoginState.WhatsAppLogin.PROGRESS -> {
                viewMvc.showProgress()
            }

            LoginState.GoogleLogin.FAILURE -> {

            }

            LoginState.GoogleLogin.PROGRESS -> {

            }

            LoginState.GoogleLogin.SUCCESS -> {

            }

            LoginState.OtpLogin.FAILURE -> {

            }

            LoginState.OtpLogin.PROGRESS -> {

            }

            LoginState.OtpLogin.SUCCESS -> {

            }
        }
    }

    private val viewListener = object : LoginViewMvc.Listener {
        override fun onWhatsAppLoginBtnClicked() {

        }

        override fun onGoogleLoginBtnClicked() {

        }

        override fun onPhoneNoInputClicked() {

        }

        override fun onPhoneNoInputEntered() {

        }

        override fun onPhoneNoLoginBtnClicked() {

        }
    }

    private val userManagerListener = object : UserManager.Listener {
        override fun onLoginSuccess() {
            setStateAndHandle(LoginState.WhatsAppLogin.SUCCESS)
        }

        override fun onLoginFailure() {
            setStateAndHandle(LoginState.WhatsAppLogin.FAILURE)
        }
    }

    fun onStop() {
        viewMvc.unregisterListener(viewListener)
        userManager.unregisterListener(userManagerListener)
    }

    fun bindViewMvc(viewMvc: LoginViewMvc) {
        this.viewMvc = viewMvc
    }
}