package ng.agrimart.android.view.util

import android.content.Context
import android.util.AttributeSet
import com.google.android.material.imageview.ShapeableImageView


class DashboardProductItemImageView: ShapeableImageView {

    constructor(context: Context): super(context)
    constructor(context: Context, attributes: AttributeSet): super(context, attributes)

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec)
        setMeasuredDimension(measuredWidth, measuredWidth)
    }

}