package ng.agrimart.android.domain.usecase.dashboard

import ng.agrimart.android.data.model.Category
import ng.agrimart.android.data.model.Product
import ng.agrimart.android.domain.model.CategoryPagedResponse
import ng.agrimart.android.domain.model.PagedResponse
import ng.agrimart.android.domain.repository.CategoryRepository
import ng.agrimart.android.domain.repository.ProductRepository

/**
 * Fetches Categories to display on the dashboard.
 */
class GetCategories(private val categoryRepository: CategoryRepository) {

    suspend fun execute(page: Int? = null, limit: Int? = null, order: String? = null): CategoryPagedResponse {
        return categoryRepository.list(page, limit, order)
    }

}
