package ng.agrimart.android.view.main.dashboard.data

import android.view.ViewGroup
import androidx.core.util.Consumer
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ng.agrimart.android.data.model.Category
import ng.agrimart.android.data.model.Product
import ng.agrimart.android.view.main.dashboard.data.category.DashboardFeedCategoryViewHolder
import ng.agrimart.android.view.main.dashboard.data.product.DashboardFeedProductViewHolder

class DashboardFeedAdapter(val onItemClickConsumer: Consumer<Any>):
    PagingDataAdapter<DashboardFeedItem, RecyclerView.ViewHolder>(DashboardFeedDiffCallback()) {

    companion object {
        const val VIEW_TYPE_CATEGORY_LIST = 1
        const val VIEW_TYPE_PRODUCT_LIST = 2
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val items = getItem(position)!!
        if (holder is DashboardFeedCategoryViewHolder && items.type == DashboardFeedItem.Type.CATEGORY_LIST) {
            holder.bind(categories = items.data as List<Category>, onItemClickConsumer = onItemClickConsumer)
        } else if (holder is DashboardFeedProductViewHolder && items.type == DashboardFeedItem.Type.PRODUCT) {
            holder.bind(products = items.data as List<Product>, onItemClickConsumer = onItemClickConsumer)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            VIEW_TYPE_CATEGORY_LIST -> return DashboardFeedCategoryViewHolder(parent)
            VIEW_TYPE_PRODUCT_LIST -> return DashboardFeedProductViewHolder(parent)
        }
        return DashboardFeedProductViewHolder(parent)
    }

    override fun getItemViewType(position: Int): Int {
        val item = getItem(position)!!
        return when (item.type) {
            DashboardFeedItem.Type.CATEGORY_LIST -> VIEW_TYPE_CATEGORY_LIST
            DashboardFeedItem.Type.PRODUCT -> VIEW_TYPE_PRODUCT_LIST
        }
    }

}

class DashboardFeedDiffCallback: DiffUtil.ItemCallback<DashboardFeedItem>() {

    override fun areItemsTheSame(oldItem: DashboardFeedItem, newItem: DashboardFeedItem): Boolean {
        return false
    }

    override fun areContentsTheSame(
        oldItem: DashboardFeedItem,
        newItem: DashboardFeedItem
    ): Boolean {
        return false
    }
}