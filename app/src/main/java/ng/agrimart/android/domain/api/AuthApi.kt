/*
 * Created by Joseph Dalughut on 23/05/2021, 12:37
 * Copyright (c) 2021 . All rights reserved.
 */

package ng.agrimart.android.domain.api

import ng.agrimart.android.domain.model.*
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AuthApi {

    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest): LoginResponse

    @POST("auth/register")
    suspend fun signup(@Body request: SignupRequest): SignupResponse

    @GET("account/email/code/request")
    suspend fun requestEmailVerification(): EmailVerificationCodeRequestResponse

    @POST("auth/verify/email")
    suspend fun verifyEmail(@Body request: VerifyEmailRequest): VerifyEmailResponse

    @POST("auth/password/reset/request")
    suspend fun requestPasswordReset(@Body request: RequestResetPasswordRequest): RequestResetPasswordResponse

    @POST("auth/password/reset")
    suspend fun resetPassword(@Body request: ResetPasswordRequest): ResetPasswordResponse

}