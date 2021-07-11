package ng.agrimart.android.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * A high-level classification for product-groups & offerings. E.g Fruits, Leafy vegetables, etc.
 * A [Product] is a lower-level classification and all belong to a [Category].
 */
@Parcelize
data class Category(
    var id: Long,
    var name: String,
    var description: String,
    var imageUrl: String
): Parcelable
