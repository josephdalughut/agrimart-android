package ng.agrimart.android.view.main.dashboard.data.category

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.util.Consumer
import androidx.recyclerview.widget.RecyclerView
import ng.agrimart.android.R
import ng.agrimart.android.data.model.Category
import ng.agrimart.android.databinding.ItemDashboardFeedCategoryBinding

/**
 * A RecyclerViewAdapter which displays Categories as a list on the dashboard.
 */
class DashboardFeedCategoryViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    val binding = ItemDashboardFeedCategoryBinding.bind(itemView)

    constructor(viewGroup: ViewGroup): this (
        itemView = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_dashboard_feed_category, viewGroup, false)
    )

    private var adapter: DashboardFeedCategoryItemAdapter? = null

    /**
     * Binds a list of Categories by displaying them in this ViewHolder.
     */
    fun bind(categories: List<Category>, onItemClickConsumer: Consumer<Any>) {
        if (adapter == null) {
            adapter = DashboardFeedCategoryItemAdapter(onItemClickConsumer)
        }
        binding.vwRecycler.adapter = adapter
        adapter?.items = categories
    }

}