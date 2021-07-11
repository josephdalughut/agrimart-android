/*
 * Created by Joseph Dalughut on 13/06/2021, 14:09
 * Copyright (c) 2021 . All rights reserved.
 */

package ng.agrimart.android.view.auth.login

import android.text.TextUtils
import android.util.Patterns
import androidx.annotation.NonNull
import androidx.lifecycle.ViewModel
import ng.agrimart.android.domain.api.AuthApi
import ng.agrimart.android.domain.auth.Authenticator
import ng.agrimart.android.data.model.AgrimartUser
import ng.agrimart.android.domain.model.LoginRequest
import ng.agrimart.android.domain.usecase.auth.loginSignup.LoginUser
import ng.agrimart.android.view.reuseable.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * The [ViewModel] which handles authentication for the [AuthActivity].
 */
@HiltViewModel
class LoginViewModel @Inject constructor(private val authenticator: Authenticator,
                                         private val authApi: AuthApi): ViewModel() {

    val events = SingleLiveEvent<Event>()
    val alerts = SingleLiveEvent<Alert>()

    /**
     * Submits the users login details for validation and eventual login.
     *
     * @param email The users email.
     * @param password The users password.
     */
    fun onSubmitLoginDetail(email: String?, password: String?) {
        // validation
        email?.let { email
            if (TextUtils.isEmpty(email) || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                alerts.postValue(Alert.ERROR_INVALID_EMAIL_ADDRESS)
                return
            }
        } ?: run {
            alerts.postValue(Alert.ERROR_INVALID_EMAIL_ADDRESS)
            return
        }

        password?.let { password
            if (TextUtils.isEmpty(password)) {
                alerts.postValue(Alert.ERROR_INVALID_PASSWORD)
                return
            }
        } ?: run {
            alerts.postValue(Alert.ERROR_INVALID_PASSWORD)
            return
        }

        // begin login
        loginUser(email, password)
    }

    fun onClickForgotPassword() {
        events.postValue(Event.ACTION_NAV_RESET_PASSWORD)
    }

    fun onClickSignup() {
        events.postValue(Event.ACTION_NAV_SIGNUP)
    }

    fun onClickLoginCloseButton() {
        events.postValue(Event.ACTION_NAV_CLOSE)
    }

    private fun loginUser(@NonNull email: String, @NonNull password: String) {
        events.postValue(Event.EVENT_LOGIN_BEGAN)

        val loginRequest = LoginRequest(email, password)
        val useCase = LoginUser(authApi, authenticator)

        GlobalScope.launch {
            try {
                useCase.execute(loginRequest)
                onLoginSuccess(authenticator.currentUser!!)
            } catch (exception: Exception) {
                exception.printStackTrace()
                onLoginError(exception)
            }
        }
    }

    private fun onLoginSuccess(user: AgrimartUser) {
        events.postValue(Event.EVENT_LOGIN_ENDED)
        events.postValue(Event.ACTION_NAV_DASHBOARD)
    }

    private fun onLoginError(exception: Exception) {
        events.postValue(Event.EVENT_LOGIN_ENDED)
        alerts.postValue(Alert.ERROR_LOGIN_FAILED)
    }

    enum class Event {
        NULL,
        EVENT_LOGIN_BEGAN,
        EVENT_LOGIN_ENDED,

        ACTION_NAV_RESET_PASSWORD,
        ACTION_NAV_SIGNUP,
        ACTION_NAV_DASHBOARD,
        ACTION_NAV_CLOSE
    }

    enum class Alert {
        ERROR_INVALID_EMAIL_ADDRESS,
        ERROR_INVALID_PASSWORD,

        ERROR_LOGIN_FAILED
    }

}
