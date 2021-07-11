/*
 * Created by Joseph Dalughut on 03/06/2021, 11:24
 * Copyright (c) 2021 . All rights reserved.
 */

package ng.agrimart.android.hilt

import android.content.Context
import ng.agrimart.android.core.AgrimartApplication
import ng.agrimart.android.core.AgrimartSecureSharedPreferences
import ng.agrimart.android.domain.api.AuthApi
import ng.agrimart.android.data.api.RetrofitApiHelper
import ng.agrimart.android.domain.auth.AndroidAccountAuthenticator
import ng.agrimart.android.domain.auth.Authenticator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApplicationModule {

    @Provides
    @Singleton
    fun provideApplicationContext(): Context = AgrimartApplication.instance

    @Provides
    @Singleton
    fun provideSharedPreferences(): AgrimartSecureSharedPreferences = AgrimartSecureSharedPreferences

    @Provides
    @Singleton
    fun provideRetrofitHelper(): RetrofitApiHelper = RetrofitApiHelper(provideAuthenticator())

    @Provides
    @Singleton
    fun provideAuthenticator(): Authenticator = AndroidAccountAuthenticator(AgrimartApplication.instance)

}
