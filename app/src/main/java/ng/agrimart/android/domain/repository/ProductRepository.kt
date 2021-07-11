package ng.agrimart.android.domain.repository

import ng.agrimart.android.domain.model.ProductPagedResponse

/**
 * A repository used to access the Product resource.
 */
interface ProductRepository {

    suspend fun list(page: Int?): ProductPagedResponse

}