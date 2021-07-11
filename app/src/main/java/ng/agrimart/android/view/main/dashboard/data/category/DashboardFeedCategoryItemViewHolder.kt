package ng.agrimart.android.view.main.dashboard.data.category

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import ng.agrimart.android.R
import ng.agrimart.android.data.model.Category
import ng.agrimart.android.databinding.ItemDashboardFeedCategoryItemBinding

/**
 * A RecyclerView [RecyclerView.ViewHolder] which displays category-items on the users dashboard.
 */
class DashboardFeedCategoryItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    constructor(viewGroup: ViewGroup) : this (
        itemView = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_dashboard_feed_category_item, viewGroup, false)
    )

    var binding: ItemDashboardFeedCategoryItemBinding = ItemDashboardFeedCategoryItemBinding.bind(itemView)

    /**
     * Binds a [Category] by displaying its contents within this view-holder instance.
     */
    fun bind(category: Category) {
        binding.txtTitle.text = category.name
        Picasso.get().load(category.imageUrl)
            .centerCrop()
            .fit()
            .into(binding.imgCover)
    }

}