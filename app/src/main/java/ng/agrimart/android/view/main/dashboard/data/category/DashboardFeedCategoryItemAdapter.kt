package ng.agrimart.android.view.main.dashboard.data.category

import android.view.ViewGroup
import androidx.core.util.Consumer
import androidx.recyclerview.widget.RecyclerView
import ng.agrimart.android.data.model.Category

/**
 * A RecyclerView Adapter wich displays Categories on the dashboard.
 */
class DashboardFeedCategoryItemAdapter(val onItemClickConsumer: Consumer<Any>):
    RecyclerView.Adapter<DashboardFeedCategoryItemViewHolder>() {

    var items: List<Category>? = null
    set(value) {
        field = value
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DashboardFeedCategoryItemViewHolder {
        return DashboardFeedCategoryItemViewHolder(parent)
    }

    override fun onBindViewHolder(holder: DashboardFeedCategoryItemViewHolder, position: Int) {
        holder.bind(items!![position])
    }

    override fun getItemCount(): Int {
        return items?.size ?: run {
            0
        }
    }
}