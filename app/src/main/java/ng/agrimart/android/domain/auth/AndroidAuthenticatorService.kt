/*
 * Created by Joseph Dalughut on 15/06/2021, 19:02
 * Copyright (c) 2021 . All rights reserved.
 */

package ng.agrimart.android.domain.auth

import android.app.Service
import android.content.Intent
import android.os.IBinder
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AndroidAuthenticatorService : Service() {

    @Inject
    lateinit var authenticator: Authenticator

    override fun onBind(intent: Intent): IBinder {
        return (authenticator as AndroidAccountAuthenticator).iBinder
    }
}