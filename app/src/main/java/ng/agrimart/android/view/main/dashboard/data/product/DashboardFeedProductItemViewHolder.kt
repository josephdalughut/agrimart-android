package ng.agrimart.android.view.main.dashboard.data.product

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import ng.agrimart.android.R
import ng.agrimart.android.data.model.Product
import ng.agrimart.android.databinding.ItemDashboardFeedProductItemBinding

/**
 * A RecyclerView ViewHolder which displays Products on the users dashboard.
 */
class DashboardFeedProductItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val binding = ItemDashboardFeedProductItemBinding.bind(itemView)

    constructor(viewGroup: ViewGroup): this (
        itemView = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_dashboard_feed_product_item, viewGroup, false)
    )

    /**
     * Binds a Product by displaying its contents in this ViewHolder instance.
     */
    fun bind(product: Product) {
        binding.txtTitle.text = product.title
        Picasso.get().load(product.imageUrl)
            .centerInside()
            .fit()
            .into(binding.imgProduct)
    }

}