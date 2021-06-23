/*
 * Created by Joseph Dalughut on 23/05/2021, 13:15
 * Copyright (c) 2021 . All rights reserved.
 */

package ng.agrimart.android.domain.usecase.auth.emailVerification

import ng.agrimart.android.domain.api.AuthApi
import ng.agrimart.android.domain.auth.Authenticator
import ng.agrimart.android.domain.repository.auth.VerifyEmailRequest

/**
 * Use-case which requests email-verification for a user.
 */
class VerifyEmailUseCase(private val authApi: AuthApi, private val authenticator: Authenticator) {

    @Throws(Exception::class)
    suspend fun execute(request: VerifyEmailRequest) {
        val response = authApi.verifyEmail(request)

        if (!response.success()) {
            throw Exception(response.message)
        }
        response.data?.agrimartUser()?.let {
            authenticator.updateUserAccount(it)
        }
        return
    }

}