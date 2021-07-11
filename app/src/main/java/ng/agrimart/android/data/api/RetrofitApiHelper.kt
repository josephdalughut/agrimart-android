/*
 * Created by Joseph Dalughut on 23/05/2021, 12:48
 * Copyright (c) 2021 . All rights reserved.
 */

package ng.agrimart.android.data.api

import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import ng.agrimart.android.core.URL_API_HOST
import ng.agrimart.android.domain.api.AuthApi
import ng.agrimart.android.domain.auth.Authenticator
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.net.ssl.HostnameVerifier

/**
 * Helper-class for creating api's using [Retrofit]
 */
class RetrofitApiHelper (authenticator: Authenticator) {

    companion object {
        private const val BASE_URL = "$URL_API_HOST/api/"
    }

    private val retrofit: Retrofit by lazy {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .hostnameVerifier(HostnameVerifier { hostname, session -> true })
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .build()
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(
                GsonBuilder()
                    .serializeNulls()
                    .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                    .create()
            ))
            .build()
    }

    private val retrofitAuthenticated: Retrofit by lazy {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor { chain ->
                authenticator.getAccessToken()?.let { accessToken ->
                    val newRequest = chain.request().newBuilder()
                        .addHeader("Authorization", "Bearer $accessToken")
                        .build()
                    chain.proceed(newRequest)
                } ?: run {
                    chain.proceed(chain.request())
                }
            }
            .hostnameVerifier(HostnameVerifier { hostname, session -> true })
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .build()
        Retrofit.Builder()
            .client(client)
            .baseUrl(BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(
                GsonBuilder()
                    .serializeNulls()
                    .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                    .create()
            ))
            .build()
    }

    /**
     * Fetches a [Retrofit] instance for creating api's.
     */
    fun getRetrofit(authenticated: Boolean = false): Retrofit {
        return if (authenticated) {
            retrofitAuthenticated
        } else {
            retrofit
        }
    }

    fun getAuthApi(authenticated: Boolean): AuthApi {
        return getRetrofit(authenticated).create(AuthApi::class.java)
    }

    fun getDashboardApi(): DashboardApi {
        return getRetrofit(true).create(DashboardApi::class.java)
    }

}