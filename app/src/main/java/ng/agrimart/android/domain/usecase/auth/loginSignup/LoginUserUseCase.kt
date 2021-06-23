/*
 * Created by Joseph Dalughut on 23/05/2021, 13:15
 * Copyright (c) 2021 . All rights reserved.
 */

package ng.agrimart.android.domain.usecase.auth.loginSignup

import ng.agrimart.android.domain.api.AuthApi
import ng.agrimart.android.domain.auth.Authenticator
import ng.agrimart.android.domain.repository.auth.LoginRequest

/**
 * Use-case which logs the user into the app.
 */
class LoginUserUseCase(private val authApi: AuthApi,
                       private val authenticator: Authenticator
) {
    @Throws(Exception::class)
    suspend fun execute(request: LoginRequest) {
        val response = authApi.login(request)

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