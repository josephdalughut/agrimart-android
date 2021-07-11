package ng.agrimart.android.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * A product group classifies product offerings into bigger categories. E.g Carrots, Beans.
 * This should not be confused with a [Category] which is a higher-level classification.
 */
@Parcelize
data class Product(var id: Long,
                   var title: String,
                   var description: String,
                   var imageUrl: String): Parcelable
