package ng.agrimart.android.domain.usecase.dashboard

import ng.agrimart.android.data.model.Product
import ng.agrimart.android.domain.model.PagedResponse
import ng.agrimart.android.domain.repository.ProductRepository

/**
 * Fetches Products to display on the dashboard.
 */
class GetDashboardProducts(private val productRepository: ProductRepository) {

    suspend fun execute(page: Int?): PagedResponse<Product> {
        return productRepository.list(page)
    }

}
