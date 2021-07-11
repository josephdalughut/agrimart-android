/*
 * Created by Joseph Dalughut on 06/05/2021, 13:52
 * Copyright (c) 2021 . All rights reserved.
 */

package ng.agrimart.android.view.launch

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ng.agrimart.android.domain.auth.Authenticator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * [ViewModel] backing the [SplashScreenActivity].
 */
@HiltViewModel
class SplashScreenViewModel @Inject constructor(val authenticator: Authenticator,
): ViewModel() {

    var navigationEvents = MutableLiveData<SplashScreenEvent?>()

    fun init() {
        GlobalScope.launch {
            authenticator.loadUserAccount()?.let {
                navigationEvents.postValue(SplashScreenEvent.NAV_DASHBOARD)
            } ?: run {
                authenticator.setAccessToken(null)
                authenticator.setRefreshToken(null)
                navigationEvents.postValue(SplashScreenEvent.NAV_ONBOARDING)
            }
        }
    }

}

/**
 * Event's which occur in the [SplashScreenViewModel]
 */
enum class SplashScreenEvent {

    NAV_DASHBOARD,
    NAV_ONBOARDING,

}