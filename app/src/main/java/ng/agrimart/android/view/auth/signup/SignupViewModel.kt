/*
 * Created by Joseph Dalughut on 13/06/2021, 14:09
 * Copyright (c) 2021 . All rights reserved.
 */

package ng.agrimart.android.view.auth.signup

import android.text.TextUtils
import android.util.Patterns
import androidx.annotation.NonNull
import androidx.lifecycle.ViewModel
import ng.agrimart.android.domain.api.AuthApi
import ng.agrimart.android.domain.auth.Authenticator
import ng.agrimart.android.domain.model.SignupRequest
import ng.agrimart.android.domain.usecase.auth.loginSignup.SignupUser
import ng.agrimart.android.view.reuseable.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * The [ViewModel] which handles authentication for the [AuthActivity].
 */
@HiltViewModel
class SignupViewModel @Inject constructor(private val authenticator: Authenticator,
                                          private val authApi: AuthApi): ViewModel() {

    val events = SingleLiveEvent<Event>()
    val alerts = SingleLiveEvent<Alert>()
    private val signupData: SignupData by lazy { SignupData() }

    /**
     * Submits the users login details for validation and eventual registration.
     *
     * @param fullName The users full name.
     * @param email The users email.
     */
    fun onSubmitSignupDetail(fullName: String?, email: String?) {
        // validation
        fullName?.let { fullName
            if (TextUtils.isEmpty(fullName) || fullName.length < 3 ) {
                alerts.postValue(Alert.ERROR_INVALID_FULL_NAME)
                return
            }
        } ?: run {
            alerts.postValue(Alert.ERROR_INVALID_FULL_NAME)
            return
        }

        email?.let { email
            if (TextUtils.isEmpty(email) || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                alerts.postValue(Alert.ERROR_INVALID_EMAIL_ADDRESS)
                return
            }
        } ?: run {
            alerts.postValue(Alert.ERROR_INVALID_EMAIL_ADDRESS)
            return
        }

        signupData.fullName = fullName
        signupData.email = email
        events.postValue(Event.ACTION_NAV_SIGNUP_PASSWORD)
    }

    /**
     * Submits the users passwords for completing their signup.
     *
     * @param password The users password.
     * @param passwordConfirmation The users password confirmation
     */
    fun onSubmitSignupPassword(password: String?, passwordConfirmation: String?) {
        password?.let { password
            if (TextUtils.isEmpty(password)) {
                alerts.postValue(Alert.ERROR_INVALID_PASSWORD)
                return
            } else if (password.length < 6) {
                alerts.postValue(Alert.ERROR_SHORT_PASSWORD)
                return
            }
        } ?: run {
            alerts.postValue(Alert.ERROR_INVALID_PASSWORD)
            return
        }

        passwordConfirmation?.let { passwordConfirmation
            if (TextUtils.isEmpty(passwordConfirmation)) {
                alerts.postValue(Alert.ERROR_INVALID_PASSWORD_CONFIIRMATION)
                return
            } else if (password != passwordConfirmation) {
                alerts.postValue(Alert.ERROR_MISMATCH_PASSWORD_CONFIIRMATION)
                return
            }
        } ?: run {
            alerts.postValue(Alert.ERROR_INVALID_PASSWORD_CONFIIRMATION)
            return
        }

        // begin registration
        signupUser(signupData.fullName!!, signupData.email!!, password)
    }

    /**
     * Called when the user clicks the close button on the [SignupDetailFragment]
     */
    fun onClickSignupDetailCloseButton() {
        events.postValue(Event.ACTION_NAV_LOGIN)
    }

    /**
     * Called when the user clicks on the close button on the [SignupPasswordFragment]
     */
    fun onClickSignupPasswordCloseButton() {
        events.postValue(Event.ACTION_NAV_SIGNUP_DETAIL)
    }

    /**
     * Called when the user clicks on the Terms & Conditions button.
     */
    fun onClickTermsAndConditionsButton() {
        events.postValue(Event.ACTION_NAV_TERMS_AND_CONDITIONS)
    }

    /**
     * Called when the user clicks on the login button.
     */
    fun onClickLoginButton() {
        events.postValue(Event.ACTION_NAV_LOGIN)
    }

    private fun signupUser(@NonNull fullName: String, @NonNull email: String, @NonNull password: String) {
        events.postValue(Event.EVENT_SIGNUP_BEGAN)

        val signupRequest = SignupRequest(fullName, email, password, password)
        val useCase = SignupUser(authApi, authenticator)

        GlobalScope.launch {
            try {
                useCase.execute(signupRequest)
                onSignupSuccess()
            } catch (exception: Exception) {
                exception.printStackTrace()
                onSignupError(exception)
            }
        }
    }

    private fun onSignupSuccess() {
        events.postValue(Event.EVENT_LOGIN_ENDED)
        events.postValue(Event.ACTION_NAV_DASHBOARD)
    }

    private fun onSignupError(exception: Exception) {
        events.postValue(Event.EVENT_SIGNUP_ENDED)
        alerts.postValue(Alert.ERROR_SIGNUP_FAILED)
    }

    enum class Event {
        EVENT_LOGIN_ENDED,
        EVENT_SIGNUP_BEGAN,
        EVENT_SIGNUP_ENDED,

        ACTION_NAV_LOGIN,
        ACTION_NAV_SIGNUP_DETAIL,
        ACTION_NAV_SIGNUP_PASSWORD,
        ACTION_NAV_DASHBOARD,
        ACTION_NAV_TERMS_AND_CONDITIONS
    }

    enum class Alert {
        ERROR_INVALID_EMAIL_ADDRESS,
        ERROR_INVALID_FULL_NAME,
        ERROR_INVALID_PASSWORD,
        ERROR_SHORT_PASSWORD,
        ERROR_INVALID_PASSWORD_CONFIIRMATION,
        ERROR_MISMATCH_PASSWORD_CONFIIRMATION,

        ERROR_SIGNUP_FAILED
    }

}

data class SignupData(var fullName: String?, var email: String?, var password: String?) {

    constructor() : this(null, null, null)

}