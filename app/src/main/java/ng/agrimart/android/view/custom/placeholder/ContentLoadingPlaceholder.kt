package ng.agrimart.android.view.custom.placeholder

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.FrameLayout
import ng.agrimart.android.R

class ContentLoadingPlaceholder @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null,
                                                          defStyleAttr: Int = 0): FrameLayout(context, attrs, defStyleAttr) {

    init {
        inflate(context, R.layout.placeholder_content_loading, this)
    }

}