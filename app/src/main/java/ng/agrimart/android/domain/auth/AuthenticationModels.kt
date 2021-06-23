/*
 * Created by Joseph Dalughut on 02/06/2021, 09:56
 * Copyright (c) 2021 . All rights reserved.
 */

package ng.agrimart.android.domain.repository.auth

import ng.agrimart.android.domain.model.AgrimartUser

/**
 * Base response-object with the user's data.
 */
data class UserData(val id: Long,
                    val name: String,
                    val email: String,
                    val phone: String?,
                    val countryCode: String?,
                    val avatar: String?,
                    val avatarThumb: String?,
                    val type: String?,
                    val emailVerified: Boolean,
                    val address: String?) {

    fun agrimartUser(): AgrimartUser {
        return AgrimartUser(id, name, email, phone, countryCode, avatar, avatarThumb, type, emailVerified, address)
    }

    companion object Mock {

        val emailUnverified: UserData
        get() {
            return UserData(1, "Bitrus Gyang", "bitrus.gyang@gmail.com", null,
                null, null, null, "individual", false, null)
        }

        val emailVerified: UserData
            get() {
                return UserData(1, "Bitrus Gyang", "bitrus.gyang@gmail.com", null,
                    null, null, null, "individual", true, null)
            }

    }

}


/**
 * Base class for responses from the Agrimart API.
 */
interface AgrimartApiResponse {

    var status: String
    var message: String

    fun success(): Boolean = status == "success"

}

/**
 * Request object for registration of a new user.
 */
data class SignupRequest(val name: String,
                         val email: String,
                         val password: String,
                         val password_confirmation: String,
                         val type: String)

/**
 * Response object for registration of a new user.
 */
data class SignupResponse(val data: UserData?,
                          override var status: String,
                          override var message: String,
                          val token: String): AgrimartApiResponse

/**
 * Request object for logging-in an existing user.
 */
data class LoginRequest(val email: String,
                        val password: String)

/**
 * Response object for logging-in an existing user.
 */
data class LoginResponse(val data: UserData?,
                         override var status: String,
                         override var message: String,
                         val token: String): AgrimartApiResponse

/**
 * Response object from requesting an email-verification.
 */
data class EmailVerificationCodeRequestResponse(override var status: String,
                                                override var message: String): AgrimartApiResponse

/**
 * Request-object for email-verification of the current user.
 */
data class VerifyEmailRequest(val otp: String)

/**
 * Response object for email-verification of the current user.
 */
data class VerifyEmailResponse(val data: UserData?,
                               override var status: String,
                               override var message: String): AgrimartApiResponse

/**
 * Request-object for requesting the reset of the password of a user.
 */
data class RequestResetPasswordRequest(val email: String)

/**
 * Response-object for requesting the reset of the password of a user.
 */
data class RequestResetPasswordResponse(override var status: String, override var message: String):
    AgrimartApiResponse

/**
 * Request-object for resetting the password of a user.
 */
data class ResetPasswordRequest(val email: String, val otp: String, val password: String,
                                val password_confirmation: String)

/**
 * Response-object for resetting the password of a user.
 */
data class ResetPasswordResponse(override var status: String, override var message: String):
    AgrimartApiResponse