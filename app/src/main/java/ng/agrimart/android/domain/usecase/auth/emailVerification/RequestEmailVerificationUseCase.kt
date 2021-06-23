/*
 * Created by Joseph Dalughut on 23/05/2021, 13:15
 * Copyright (c) 2021 . All rights reserved.
 */

package ng.agrimart.android.domain.usecase.auth.emailVerification

import ng.agrimart.android.domain.api.AuthApi

/**
 * Use-case which requests email-verification for a user.
 */
class RequestEmailVerificationUseCase(private val authApi: AuthApi) {
    @Throws(Exception::class)
    suspend fun execute() {
        val response = authApi.requestEmailVerification()

        if (!response.success()) {
            throw Exception(response.message)
        }
        return
    }

}