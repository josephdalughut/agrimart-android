package ng.agrimart.android.view.main.dashboard.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import ng.agrimart.android.domain.repository.DashboardRepository
import ng.agrimart.android.domain.usecase.dashboard.GetFeed

/**
 * A pagign-data-source which loads dashboard feed data for the users dashboard.
 */
class DashboardFeedDataSource(private val startPage: Int = 1,
                              private val dashboardRepository: DashboardRepository):
    PagingSource<Int, DashboardFeedItem>() {

//    override val keyReuseSupported: Boolean
//        get() = true

    override fun getRefreshKey(state: PagingState<Int, DashboardFeedItem>): Int? {
        return startPage
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, DashboardFeedItem> {
        // TODO run this in try catch
        val result =  GetFeed(dashboardRepository).execute(page = params.key)
        val products = result.data
        val categories = result.categories
        val items = mutableListOf<DashboardFeedItem>()
        if (result.data.size > 2 && params.key == 1) {
            DashboardFeedItem(products.subList(0, 2), DashboardFeedItem.Type.PRODUCT).also {
                items.add(it)
            }
            categories?.let {
                if (it.isNotEmpty()) {
                    DashboardFeedItem(it, DashboardFeedItem.Type.CATEGORY_LIST).also { item ->
                        items.add(item)
                    }
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

        var nextPage: Int? = result.currentPage + 1
        if (result.data.isEmpty()) nextPage = null
        return LoadResult.Page(items, null, nextPage)
    }

}
