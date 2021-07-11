/*
 * Created by Joseph Dalughut on 06/05/2021, 13:40
 * Copyright (c) 2021 . All rights reserved.
 */

package ng.agrimart.android.domain.auth

import ng.agrimart.android.data.model.AgrimartUser

/**
 * Helper class for managing and authenticating the current [AgrimartUser] account for the application.
 */
interface Authenticator {

    /**
     * The current [AgrimartUser] of the application.
     */
    var currentUser: AgrimartUser?

    /**
     * Loads the current [AgrimartUser]. After this completes successfully, you'll be able to
     * access the user from the [Authenticator.currentUser] value.
     */
    suspend fun loadUserAccount(): AgrimartUser?

    /**
     * Saves a [AgrimartUser] as the current account for the application.
     */
    suspend fun setUserAccount(user: AgrimartUser)

    /**
     * Updates the current [AgrimartUser]'s account.
     */
    suspend fun updateUserAccount(user: AgrimartUser)

    /**
     * Removes the current [AgrimartUser] account on the application. This is equivalent to logging-out
     * the current user.
     */
    suspend fun removeUserAccount()

    /**
     * Fetches the users access token.
     */
    fun getAccessToken(): String?

    /**
     * Sets the users access token
     * @param token The users access token.
     */
    fun setAccessToken(token: String?)

    /**
     * Sets the users refresh token
     * @param token The users refresh token.
     */
    fun setRefreshToken(token: String?)

    /**
     * Fetches the users refresh token.
     */
    fun getRefreshToken(): String?

}