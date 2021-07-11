package ng.agrimart.android.data.source

import ng.agrimart.android.data.api.ProductApi
import ng.agrimart.android.data.model.Product
import ng.agrimart.android.domain.model.PagedResponse
import ng.agrimart.android.domain.model.ProductPagedResponse

/**
 * A data-source used to interact with the [Product] resource
 */
interface ProductDataSource {

    /**
     * Lists the Products in the datasource.
     */
    suspend fun list(page: Int?): ProductPagedResponse

}

/**
 * A [ProductDataSource] which interacts with the remote API & data-source.
 * @see ProductDataSource
 */
class RemoteProductDataSource(val productApi: ProductApi): ProductDataSource {

    override suspend fun list(page: Int?): ProductPagedResponse {
        return productApi.list(page)
    }

}