/*
 * Created by Joseph Dalughut on 13/06/2021, 14:31
 * Copyright (c) 2021 . All rights reserved.
 */

package ng.agrimart.android.view.reuseable

import androidx.annotation.StringRes

/**
 * A container for popup messages which can be sent from a [ViewModel] to the
 * view layer.
 */
data class PopupStringResource(@StringRes val title: Int?,
                               @StringRes val message: Int?,
                               val type: Type
                               ) {


    enum class Type {
        SUCCESS,
        ERROR,
        INFO
    }

}