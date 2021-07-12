package ng.agrimart.android.view.main.catalog.data

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import ng.agrimart.android.R
import ng.agrimart.android.data.model.Category
import ng.agrimart.android.databinding.ItemDashboardFeedCategoryItemBinding

/**
 * A RecyclerView [RecyclerView.ViewHolder] which displays category-items on the Catalog screen..
 */
class CatalogCategoryItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    constructor(viewGroup: ViewGroup) : this (
        itemView = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_catalog_category_item, viewGroup, false)
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
            .placeholder(R.drawable.placeholder_dashboard_product)
            .into(binding.imgCover)
    }

}