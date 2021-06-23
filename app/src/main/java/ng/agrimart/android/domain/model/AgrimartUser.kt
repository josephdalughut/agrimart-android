/*
 * Created by Joseph Dalughut on 06/05/2021, 13:28
 * Copyright (c) 2021 . All rights reserved.
 */

package ng.agrimart.android.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * A common user of the Agrimart Application
 */
@Parcelize
data class AgrimartUser(var id: Long,
                    var name: String,
                    var email: String,
                    var phone: String?,
                    var countryCode: String?,
                    var avatar: String?,
                    var avatarThumb: String?,
                    var type: String?,
                    var emailVerified: Boolean,
                    var address: String?
): Parcelable
