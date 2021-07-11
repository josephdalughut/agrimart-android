/*
 * Created by Joseph Dalughut on 23/05/2021, 13:15
 * Copyright (c) 2021 . All rights reserved.
 */

package ng.agrimart.android.domain.usecase.auth.resetPassword

import ng.agrimart.android.domain.api.AuthApi
import ng.agrimart.android.domain.model.RequestResetPasswordRequest
import kotlin.jvm.Throws

/**
 * Use-case which requests a password reset for a user.
 */
class RequestPasswordReset(private val authApi: AuthApi) {
    @Throws(Exception::class)
    suspend fun execute(request: RequestResetPasswordRequest) {
        val response = authApi.requestPasswordReset(request)

        if (!response.success()) {
            throw Exception(response.message)
        }
        return
    }

}