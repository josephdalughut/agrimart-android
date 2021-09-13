package ng.agrimart.android.view.custom.placeholder

import android.content.Context
import android.util.AttributeSet
import android.view.View
import ng.agrimart.android.R

class ContentLoadingErrorPlaceholder @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null,
                                                               defStyleAttr: Int = 0): View(context, attrs) {

    init {
        inflate(context, R.layout.placeholder_content_loading_error, null)
    }

}