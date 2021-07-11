package ng.agrimart.android.domain.usecase.dashboard

import ng.agrimart.android.data.model.Category
import ng.agrimart.android.data.model.Product
import ng.agrimart.android.domain.model.PagedResponse
import ng.agrimart.android.domain.repository.CategoryRepository
import ng.agrimart.android.domain.repository.ProductRepository

/**
 * Fetches Categories to display on the dashboard.
 */
class GetDashboardCategories(private val categoryRepository: CategoryRepository) {

    suspend fun execute(page: Int?, limit: Int?): List<Category> {
        return categoryRepository.list(page, limit).data
    }

}
