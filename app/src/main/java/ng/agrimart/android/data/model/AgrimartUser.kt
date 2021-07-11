/*
 * Created by Joseph Dalughut on 06/05/2021, 13:28
 * Copyright (c) 2021 . All rights reserved.
 */

package ng.agrimart.android.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * A common user of the Agrimart Application
 */
@Parcelize
data class AgrimartUser(var id: Long,
                    var name: String,
                    var email: String,
                    var avatar: String?,
                    var emailVerified: Boolean
): Parcelable
