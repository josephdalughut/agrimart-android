/*
 * Created by Joseph Dalughut on 15/06/2021, 18:27
 * Copyright (c) 2021 . All rights reserved.
 */

package ng.agrimart.android.domain.api

import ng.agrimart.android.domain.repository.auth.*
import kotlinx.coroutines.delay

class AuthApiMock: AuthApi {

    val defaultStatus = "success"
    val delayTime = 3000L

    override suspend fun login(request: LoginRequest): LoginResponse {
        delay(delayTime)
        return LoginResponse(UserData.emailUnverified, defaultStatus, "Login Successful", "token")
    }

    override suspend fun signup(request: SignupRequest): SignupResponse {
        delay(delayTime)
        return SignupResponse(UserData.emailUnverified, defaultStatus, "Success", "token")
    }

    override suspend fun requestEmailVerification(): EmailVerificationCodeRequestResponse {
        delay(delayTime)
        return EmailVerificationCodeRequestResponse(defaultStatus, "Success")
    }

    override suspend fun verifyEmail(request: VerifyEmailRequest): VerifyEmailResponse {
        delay(delayTime)
        return VerifyEmailResponse(UserData.emailVerified, defaultStatus, "Success")
    }

    override suspend fun requestPasswordReset(request: RequestResetPasswordRequest): RequestResetPasswordResponse {
        delay(delayTime)
        return RequestResetPasswordResponse(defaultStatus, "Success")
    }

    override suspend fun resetPassword(request: ResetPasswordRequest): ResetPasswordResponse {
        delay(delayTime)
        return ResetPasswordResponse(defaultStatus, "Success")
    }

}