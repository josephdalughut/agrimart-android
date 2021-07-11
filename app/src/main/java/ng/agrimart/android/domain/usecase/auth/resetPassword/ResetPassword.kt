/*
 * Created by Joseph Dalughut on 23/05/2021, 13:15
 * Copyright (c) 2021 . All rights reserved.
 */

package ng.agrimart.android.domain.usecase.auth.resetPassword

import ng.agrimart.android.domain.api.AuthApi
import ng.agrimart.android.domain.model.ResetPasswordRequest
import kotlin.jvm.Throws

/**
 * Use-case which resets the users password.
 */
class ResetPassword(private val authApi: AuthApi) {
    @Throws(Exception::class)
    suspend fun execute(request: ResetPasswordRequest) {
        val response = authApi.resetPassword(request)

        if (!response.success()) {
            throw Exception(response.message)
        }
        return
    }

}