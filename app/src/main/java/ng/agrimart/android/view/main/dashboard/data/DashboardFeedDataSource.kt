package ng.agrimart.android.view.main.dashboard.data

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import ng.agrimart.android.domain.repository.CategoryRepository
import ng.agrimart.android.domain.repository.ProductRepository
import ng.agrimart.android.domain.usecase.dashboard.GetCategories
import ng.agrimart.android.domain.usecase.dashboard.GetDashboardProducts
import ng.agrimart.android.view.util.ContentLoaderCallbacks

/**
 * A pagign-data-source which loads dashboard feed data for the users dashboard.
 */
class DashboardFeedDataSource(private val startPage: Int = 1,
                              private val productRepository: ProductRepository,
                              private val categoryRepository: CategoryRepository,
                              private val loaderCallbacks: ContentLoaderCallbacks?):
    PagingSource<Int, DashboardFeedItem>() {

    override fun getRefreshKey(state: PagingState<Int, DashboardFeedItem>): Int? {
        return startPage
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, DashboardFeedItem> {
        Log.d(LOG_TAG, "Params key ${params.key}")
        Log.d(LOG_TAG, "Start page $startPage")
        if (params.key == null || params.key == startPage) {
            loaderCallbacks?.onContentLoadBegan()
        } else {
            loaderCallbacks?.onContentLoadMore()
        }

        try {
            Log.d(LOG_TAG, "Starting try")
            val productResponse =  GetDashboardProducts(productRepository).execute(page = params.key)
            val products = productResponse.data
            Log.d(LOG_TAG, "Got products")

            val items = mutableListOf<DashboardFeedItem>()
            Log.d(LOG_TAG, "Bulind categories")
            if (productResponse.data.size > 2 && (params.key == null || params.key == 1)) {
                DashboardFeedItem(products.subList(0, 2), DashboardFeedItem.Type.PRODUCT).also {
                    items.add(it)
                }

                val categories = GetCategories(categoryRepository).execute(null, 5).data
                if (categories.isNotEmpty()) {
                    DashboardFeedItem(categories, DashboardFeedItem.Type.CATEGORY_LIST).also { item ->
                        items.add(item)
                    }
                }
                Log.d(LOG_TAG, "Categoied: ${categories}")

                DashboardFeedItem(products.subList(3, products.size - 1), DashboardFeedItem.Type.PRODUCT).also {
                    items.add(it)
                }
            } else {
                Log.d(LOG_TAG, "No categrories just products")
                DashboardFeedItem(products, DashboardFeedItem.Type.PRODUCT).also {
                    items.add(it)
                }
            }

            var nextPage: Int? = productResponse.currentPage + 1
            if (productResponse.data.isEmpty()) nextPage = null

            loaderCallbacks?.onContentLoadSuccess()
            return LoadResult.Page(items, null, nextPage)
        } catch (exception: Exception) {
            exception.printStackTrace()
            // if this isn't the first list of items, skip error
                if (params.key == startPage) {
                    loaderCallbacks?.onContentLoadError(exception)
                } else {
                    Log.d(LOG_TAG, "No callback on error, key is ${params.key}, startPage: $startPage")
                }

            return LoadResult.Error(exception)
        }
    }

    companion object {
        val LOG_TAG: String = DashboardFeedDataSource::class.java.simpleName
    }

}
