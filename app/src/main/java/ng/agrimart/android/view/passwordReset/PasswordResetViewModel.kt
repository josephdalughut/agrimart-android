/*
 * Created by Joseph Dalughut on 14/06/2021, 04:33
 * Copyright (c) 2021 . All rights reserved.
 */

package ng.agrimart.android.view.passwordReset

import android.text.TextUtils
import android.util.Patterns
import androidx.annotation.NonNull
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ng.agrimart.android.domain.api.AuthApi
import ng.agrimart.android.domain.model.RequestResetPasswordRequest
import ng.agrimart.android.domain.model.ResetPasswordRequest
import ng.agrimart.android.domain.usecase.auth.resetPassword.RequestPasswordReset
import ng.agrimart.android.domain.usecase.auth.resetPassword.ResetPassword
import ng.agrimart.android.view.util.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.Exception

/**
 * [ViewModel] handling business-logic for the password-reset feature
 */
@HiltViewModel
class PasswordResetViewModel @Inject constructor(val authApi: AuthApi)
    : ViewModel() {

    val currentResetEmail = MutableLiveData<String>()
    val events = SingleLiveEvent<Event>()
    val alerts = SingleLiveEvent<Alert>()
    var currentRequestPasswordResetRequest: RequestResetPasswordRequest? = null

    /**
     * Submits the users email to request for a password reset.
     *
     * @param email The users email.
     */
    fun onSubmitUserEmail(email: String?) {
        email?.let { email
            if (TextUtils.isEmpty(email) || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                alerts.postValue(Alert.ERROR_INVALID_EMAIL_ADDRESS)
                return
            }
        } ?: run {
            alerts.postValue(Alert.ERROR_INVALID_EMAIL_ADDRESS)
            return
        }
        currentResetEmail.postValue(email!!)
        requestPasswordReset(email, false)
    }

    private fun requestPasswordReset(@NonNull email: String, isRetry: Boolean) {
        if (isRetry) {
            events.postValue(Event.REQUEST_RESET_RETRY_BEGAN)
        } else {
            events.postValue(Event.REQUEST_RESET_BEGAN)
        }

        val request = RequestResetPasswordRequest(email)
        this.currentRequestPasswordResetRequest = request
        val useCase = RequestPasswordReset(authApi)

        GlobalScope.launch {
            try {
                useCase.execute(request)
                onRequestPasswordResetSuccess()
            } catch (exception: Exception) {
                exception.printStackTrace()
                onRequestPasswordResetError(exception)
            }
        }
    }

    private fun onRequestPasswordResetSuccess() {
        events.postValue(Event.REQUEST_RESET_ENDED)
        events.postValue(Event.ACTION_NAV_PASSWORD_RESET)
    }

    private fun onRequestPasswordResetError(exception: Exception) {
        events.postValue(Event.REQUEST_RESET_ENDED)
        alerts.postValue(Alert.REQUEST_RESET_ERROR)
    }

    fun onClickCloseRequestResetButton() {
        events.postValue(Event.ACTION_NAV_CLOSE)
    }

    fun onClickCloseResetPasswordButton() {
        events.postValue(Event.ACTION_NAV_REQUEST_RESET)
    }


    /**
     * Submits the user details for a password reset.
     *
     * @param otp A 6-digit One-Time-Password sent to the users email address.
     * @param password The users new password.
     * @param passwordConfirmation A confirmation of the users password.
     */
    fun onSubmitPasswordResetDetail(otp: String?, password: String?, passwordConfirmation: String?) {
        otp?.let {
            if (TextUtils.isEmpty(it) || it.length != 6) {
                alerts.postValue(Alert.ERROR_OTP_INVALID)
                return
            }
        } ?: run {
            alerts.postValue(Alert.ERROR_OTP_INVALID)
            return
        }

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

        resetPassword(otp, password)
    }

    /**
     * Resends the users OTP to their email.
     */
    fun onClickResendOTP() {
        requestPasswordReset(currentResetEmail.value!!, true)
    }

    private fun resetPassword(@NonNull otp: String, @NonNull password: String) {
        events.postValue(Event.RESET_BEGAN)

        val request = ResetPasswordRequest(currentResetEmail.value!!, otp, password, password)
        val useCase = ResetPassword(authApi)

        GlobalScope.launch {
            try {
                useCase.execute(request)
                onResetPasswordSuccess()
            }catch (exception: Exception) {
                exception.printStackTrace()
                onResetPasswordError(exception)
            }
        }
    }

    private fun onResetPasswordSuccess() {
        events.postValue(Event.RESET_ENDED)
        alerts.postValue(Alert.RESET_SUCCESS)
    }

    private fun onResetPasswordError(exception: Exception) {
        events.postValue(Event.RESET_ENDED)
        alerts.postValue(Alert.RESET_ERROR)
    }

    enum class Event {
        REQUEST_RESET_BEGAN,
        REQUEST_RESET_ENDED,
        REQUEST_RESET_RETRY_BEGAN,

        RESET_BEGAN,
        RESET_ENDED,

        ACTION_NAV_CLOSE,
        ACTION_NAV_PASSWORD_RESET,
        ACTION_NAV_REQUEST_RESET
    }

    enum class Alert {
        ERROR_INVALID_EMAIL_ADDRESS,
        ERROR_OTP_INVALID,
        ERROR_INVALID_PASSWORD,
        ERROR_SHORT_PASSWORD,
        ERROR_INVALID_PASSWORD_CONFIIRMATION,
        ERROR_MISMATCH_PASSWORD_CONFIIRMATION,

        REQUEST_RESET_ERROR,

        RESET_SUCCESS,
        RESET_ERROR
    }
}