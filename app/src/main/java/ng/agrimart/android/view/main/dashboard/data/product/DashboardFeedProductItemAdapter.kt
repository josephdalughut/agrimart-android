package ng.agrimart.android.view.main.dashboard.data.product

import android.util.Log
import android.view.ViewGroup
import androidx.core.util.Consumer
import androidx.recyclerview.widget.RecyclerView
import ng.agrimart.android.data.model.Product

class DashboardFeedProductItemAdapter(val onItemClickListener: Consumer<Any>):
    RecyclerView.Adapter<DashboardFeedProductItemViewHolder>() {

    var items: List<Product>? = null
        set(value) {
            field = value
            notifyDataSetChanged()
            Log.d(LOG_TAG, "Size of objects: ${value!!.size}")
        }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DashboardFeedProductItemViewHolder {
        return DashboardFeedProductItemViewHolder(parent)
    }

    override fun onBindViewHolder(holder: DashboardFeedProductItemViewHolder, position: Int) {
        holder.bind(items!![position])
    }

    override fun getItemCount(): Int {
        return items?.size ?: run {
            0
        }
    }

    companion object {
        val LOG_TAG = DashboardFeedProductItemAdapter::class.java.simpleName
    }

}