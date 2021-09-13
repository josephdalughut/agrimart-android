/*
 * Created by Joseph Dalughut on 02/06/2021, 02:59
 * Copyright (c) 2021 . All rights reserved.
 */

package ng.agrimart.android.core

import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey

/**
 * A helper class for storing credentials securely.
 */
object AgrimartSecureSharedPreferences {

    const val sharedPrefsFile = "agrimartsecprefs"
    private val mainKey = MasterKey.Builder(AgrimartApplication.instance)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()
    private val sharedPreferences: SharedPreferences by lazy {
        EncryptedSharedPreferences.create(
            AgrimartApplication.instance,
            sharedPrefsFile,
            mainKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    /**
     * Fetches the [SharedPreferences.Editor] backing this.
     */
    fun edit(): SharedPreferences.Editor {
        return sharedPreferences.edit()
    }

    /**
     * Fetches the [SharedPreferences] backing this.
     */
    fun get(): SharedPreferences {
        return sharedPreferences;
    }

}