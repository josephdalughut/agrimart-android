package ng.agrimart.android.view.main.dashboard.data.product

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.util.Consumer
import androidx.recyclerview.widget.RecyclerView
import me.farahani.spaceitemdecoration.SpaceItemDecoration
import ng.agrimart.android.R
import ng.agrimart.android.data.model.Product
import ng.agrimart.android.databinding.ItemDashboardFeedProductBinding

class DashboardFeedProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val binding = ItemDashboardFeedProductBinding.bind(itemView)

    constructor(viewGroup: ViewGroup): this (
        itemView = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_dashboard_feed_product, viewGroup, false)
    )

    private var adapter: DashboardFeedProductItemAdapter? = null

    /**
     * Binds a list of Categories by displaying them in this ViewHolder.
     */
    fun bind(products: List<Product>, onItemClickConsumer: Consumer<Any>) {
        if (adapter == null) {
            adapter = DashboardFeedProductItemAdapter(onItemClickConsumer)
        }
        binding.vwRecycler.adapter = adapter
        binding.vwRecycler.addItemDecoration(SpaceItemDecoration(itemView.resources
            .getDimensionPixelSize(R.dimen.spacing_dashboard_product_item), true))
        adapter?.items = products
    }

}