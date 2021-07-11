package ng.agrimart.android.data.source

import ng.agrimart.android.data.api.CategoryApi
import ng.agrimart.android.data.api.ProductApi
import ng.agrimart.android.data.model.Category
import ng.agrimart.android.data.model.Product
import ng.agrimart.android.domain.model.CategoryPagedResponse
import ng.agrimart.android.domain.model.PagedResponse

/**
 * A data-source used to interact with the Category resource.
 */
interface CategoryDataSource {

    /**
     * Lists the Categories available in the data-source.
     */
    suspend fun list(page: Int?, limit: Int?): CategoryPagedResponse

}

/**
 * A [CategoryDataSource] which interacts with the remote API & data-source.
 * @see CategoryDataSource
 */
class RemoteCategoryDataSource(private val categoryApi: CategoryApi): CategoryDataSource {

    override suspend fun list(page: Int?, limit: Int?): CategoryPagedResponse {
        return categoryApi.list(page, limit)
    }

}