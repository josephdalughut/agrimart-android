/*
 * Created by Joseph Dalughut on 23/05/2021, 12:48
 * Copyright (c) 2021 . All rights reserved.
 */

package ng.agrimart.android.domain.api

import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
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
        private const val BASE_URL = "http://3.89.138.187/api/v1/"
    }

    private val retrofit: Retrofit by lazy {
        val client = OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor())
            .hostnameVerifier(HostnameVerifier { hostname, session -> true })
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .build()
        Retrofit.Builder()
            .baseUrl(Companion.BASE_URL)
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
        val client = OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor())
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
            .baseUrl(Companion.BASE_URL)
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

}