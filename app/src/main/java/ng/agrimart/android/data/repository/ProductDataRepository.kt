package ng.agrimart.android.data.repository

import ng.agrimart.android.data.api.ProductApi
import ng.agrimart.android.data.model.Product
import ng.agrimart.android.data.source.ProductDataSource
import ng.agrimart.android.data.source.RemoteProductDataSource
import ng.agrimart.android.domain.model.CategoryPagedResponse
import ng.agrimart.android.domain.model.PagedResponse
import ng.agrimart.android.domain.model.ProductPagedResponse
import ng.agrimart.android.domain.repository.ProductRepository

class ProductDataRepository constructor(val productApi: ProductApi): ProductRepository {

    val remoteDataSource: ProductDataSource = RemoteProductDataSource(productApi)

    override suspend fun list(page: Int?): ProductPagedResponse {
        return remoteDataSource.list(page)
    }

}