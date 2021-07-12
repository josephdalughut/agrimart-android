package ng.agrimart.android.view.main.catalog.data

import android.view.ViewGroup
import androidx.core.util.Consumer
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import ng.agrimart.android.data.model.Category

class CatalogCategoryAdapter(val onItemClickConsumer: Consumer<Any>):
    PagingDataAdapter<Category, CatalogCategoryItemViewHolder>(CatalogCategoryDiffCallback()) {

    override fun onBindViewHolder(holder: CatalogCategoryItemViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
        holder.itemView.setOnClickListener {
            getItem(holder.bindingAdapterPosition)?.let {
                onItemClickConsumer.accept(it)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatalogCategoryItemViewHolder {
        return CatalogCategoryItemViewHolder(parent)
    }

}

class CatalogCategoryDiffCallback: DiffUtil.ItemCallback<Category>() {

    override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: Category,
        newItem: Category
    ): Boolean {
        return oldItem.id == newItem.id
    }
}