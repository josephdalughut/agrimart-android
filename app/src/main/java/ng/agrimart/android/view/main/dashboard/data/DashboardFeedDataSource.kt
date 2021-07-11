package ng.agrimart.android.view.main.dashboard.data

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import ng.agrimart.android.domain.repository.CategoryRepository
import ng.agrimart.android.domain.repository.ProductRepository
import ng.agrimart.android.domain.usecase.dashboard.GetDashboardCategories
import ng.agrimart.android.domain.usecase.dashboard.GetDashboardProducts

/**
 * A pagign-data-source which loads dashboard feed data for the users dashboard.
 */
class DashboardFeedDataSource(private val startPage: Int = 1,
                              private val productRepository: ProductRepository,
                              private val categoryRepository: CategoryRepository):
    PagingSource<Int, DashboardFeedItem>() {

    //    override val keyReuseSupported: Boolean
//        get() = true

    override fun getRefreshKey(state: PagingState<Int, DashboardFeedItem>): Int? {
        return startPage
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, DashboardFeedItem> {
        // TODO run this in try catch
        val productResponse =  GetDashboardProducts(productRepository).execute(page = params.key)
        val products = productResponse.data

        val items = mutableListOf<DashboardFeedItem>()
        if (productResponse.data.size > 2 && (params.key == null || params.key == 1)) {
            DashboardFeedItem(products.subList(0, 2), DashboardFeedItem.Type.PRODUCT).also {
                items.add(it)
            }

            val categories = GetDashboardCategories(categoryRepository).execute(null, 5)
            if (categories.isNotEmpty()) {
                DashboardFeedItem(categories, DashboardFeedItem.Type.CATEGORY_LIST).also { item ->
                    items.add(item)
                }
            }

            DashboardFeedItem(products.subList(3, products.size - 1), DashboardFeedItem.Type.PRODUCT).also {
                items.add(it)
            }
        } else {
            DashboardFeedItem(products, DashboardFeedItem.Type.PRODUCT).also {
                items.add(it)
            }
        }

        var nextPage: Int? = productResponse.currentPage + 1
        if (productResponse.data.isEmpty()) nextPage = null
        return LoadResult.Page(items, null, nextPage)
    }

    companion object {
        val LOG_TAG = DashboardFeedDataSource::class.java.simpleName
    }

}
