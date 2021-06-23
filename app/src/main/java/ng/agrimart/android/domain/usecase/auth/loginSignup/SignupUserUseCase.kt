/*
 * Created by Joseph Dalughut on 14/06/2021, 04:26
 * Copyright (c) 2021 . All rights reserved.
 */

package ng.agrimart.android.domain.usecase.auth.loginSignup

import ng.agrimart.android.domain.api.AuthApi
import ng.agrimart.android.domain.auth.Authenticator
import ng.agrimart.android.domain.repository.auth.SignupRequest

/**
 * Use-case which registers a user into the app.
 */
class SignupUserUseCase(private val authApi: AuthApi,
                        private val authenticator: Authenticator) {
    suspend fun execute(request: SignupRequest) {
        val response = authApi.signup(request)

        if (!response.success()) {
            throw Exception(response.message)
        }

        response.data?.agrimartUser()?.let{
            authenticator.setUserAccount(it)
        }.run {
            authenticator.setAccessToken(response.token)
        }
        return
    }

}