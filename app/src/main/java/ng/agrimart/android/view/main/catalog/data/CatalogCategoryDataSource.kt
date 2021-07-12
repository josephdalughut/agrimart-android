package ng.agrimart.android.view.main.catalog.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import ng.agrimart.android.data.model.Category
import ng.agrimart.android.domain.repository.CategoryRepository
import ng.agrimart.android.domain.usecase.dashboard.GetCategories
import ng.agrimart.android.domain.usecase.dashboard.GetDashboardProducts

/**
 * A pagign-data-source which loads dashboard feed data for the users dashboard.
 */
class CatalogCategoryDataSource(private val startPage: Int = 1,
                                private val categoryRepository: CategoryRepository):
    PagingSource<Int, Category>() {

    override fun getRefreshKey(state: PagingState<Int, Category>): Int? {
        return startPage
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Category> {
        // TODO run this in try catch
        val categoryResponse =  GetCategories(categoryRepository).execute(page = params.key)
        val categories = categoryResponse.data
        var nextPage: Int? = categoryResponse.currentPage + 1
        if (categories.isEmpty()) nextPage = null
        return LoadResult.Page(categories, null, nextPage)
    }

    companion object {
        val LOG_TAG = CatalogCategoryDataSource::class.java.simpleName
    }

}
