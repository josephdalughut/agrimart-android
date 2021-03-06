/*
 * Created by Joseph Dalughut on 23/05/2021, 15:08
 * Copyright (c) 2021 . All rights reserved.
 */

package ng.agrimart.android.domain.auth

import android.accounts.AbstractAccountAuthenticator
import android.accounts.Account
import android.accounts.AccountAuthenticatorResponse
import android.accounts.AccountManager
import android.content.Context
import android.os.Bundle
import android.util.Log
import com.google.gson.Gson
import ng.agrimart.android.core.AgrimartSecureSharedPreferences
import ng.agrimart.android.domain.model.AgrimartUser

/**
 * An [Authenticator] backed by Android's own [AbstractAccountAuthenticator] system.
 */
class AndroidAccountAuthenticator(context: Context) :
    AbstractAccountAuthenticator(context), Authenticator {

    companion object Constants {
        const val ACCOUNT_TYPE = "Agrimart"
        const val ACCESS_TOKEN_KEY = "access_token"
        const val PASSCODE_KEY = "passcode"

        const val LOG_TAG = "AndroidAuth"
    }

    private val accountManager: AccountManager by lazy {
        AccountManager.get(context)
    }

    private var _currentUser: AgrimartUser? = null
    override var currentUser: AgrimartUser?
        get() = _currentUser
        set(value) { _currentUser = value }

    override suspend fun loadUserAccount(): AgrimartUser? {
        accountManager.getAccountsByType(ACCOUNT_TYPE)
            .also {
                if (it.isEmpty()) {
                    return null
                }
                it.first().also { account ->
                    val data = accountManager.getUserData(account, "data")
                    Log.d(LOG_TAG, data)
                    return data.let { jsonData ->
                        currentUser = Gson().fromJson(jsonData, AgrimartUser::class.java)
                        currentUser
                    }
                }
            }
    }

    override suspend fun setUserAccount(user: AgrimartUser) {
        Account(user.email, ACCOUNT_TYPE).also {
            val data = Gson().toJson(user)
            val dataBundle = Bundle().apply {
                putString("data", data)
            }
            accountManager.addAccountExplicitly(it, "", dataBundle )
        }
        currentUser = user
    }

    override suspend fun updateUserAccount(user: AgrimartUser) {
        Log.d(LOG_TAG, "Email: ${user.emailVerified}")
        Account(user.email, ACCOUNT_TYPE).also {
            val data = Gson().toJson(user)
            Log.d(LOG_TAG, "Updating user data: $data")
            accountManager.setUserData(it, "data", data)
        }
        currentUser = user
    }

    override suspend fun removeUserAccount() {
        loadUserAccount()?.let {
            Account(it.email, ACCOUNT_TYPE).also { account ->
                accountManager.removeAccountExplicitly(account)
            }
        }.also {
            this.currentUser = null
        }
    }

    override fun getAccessToken(): String? {
        return AgrimartSecureSharedPreferences.get().getString(ACCESS_TOKEN_KEY, null)
    }

    override fun setAccessToken(token: String?) {
        token?.let {
            AgrimartSecureSharedPreferences.edit().putString(ACCESS_TOKEN_KEY, it).apply()
        } ?: run {
            AgrimartSecureSharedPreferences.edit().remove(ACCESS_TOKEN_KEY).apply()
        }
        return
    }

    override fun getPasscode(): String? {
        return AgrimartSecureSharedPreferences.get().getString(PASSCODE_KEY, null)
    }

    override fun setPasscode(passcode: String?) {
        passcode?.let {
            AgrimartSecureSharedPreferences.edit().putString(PASSCODE_KEY, it).apply()
        } ?: run {
            AgrimartSecureSharedPreferences.edit().remove(PASSCODE_KEY).apply()
        }
        return
    }

    override fun hasValidPasscode(): Boolean {
        return !getPasscode().isNullOrEmpty()
    }

    // UNUSED

    override fun editProperties(response: AccountAuthenticatorResponse?, accountType: String?): Bundle {
        TODO("Not yet implemented") }

    override fun addAccount(response: AccountAuthenticatorResponse?, accountType: String?, authTokenType: String?,
                            requiredFeatures: Array<out String>?, options: Bundle?): Bundle {
        TODO("Not yet implemented") }

    override fun confirmCredentials(response: AccountAuthenticatorResponse?, account: Account?,
                                    options: Bundle?): Bundle { TODO("Not yet implemented") }

    override fun getAuthToken(response: AccountAuthenticatorResponse?, account: Account?, authTokenType: String?,
                              options: Bundle?): Bundle {
        TODO("Not yet implemented") }

    override fun getAuthTokenLabel(authTokenType: String?): String {
        TODO("Not yet implemented") }

    override fun updateCredentials(response: AccountAuthenticatorResponse?, account: Account?, authTokenType: String?,
                                   options: Bundle?): Bundle {
        TODO("Not yet implemented") }

    override fun hasFeatures(response: AccountAuthenticatorResponse?, account: Account?, features: Array<out String>?
    ): Bundle { TODO("Not yet implemented") }

}