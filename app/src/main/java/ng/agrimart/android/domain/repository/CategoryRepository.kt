package ng.agrimart.android.domain.repository

import ng.agrimart.android.data.model.Category
import ng.agrimart.android.domain.model.CategoryPagedResponse

/**
 * A repository for interacting with the [Category] resource.
 */
interface CategoryRepository {

    /**
     * Lists the Categories in tis repository.
     */
    suspend fun list(page: Int?, limit: Int?, order: String?): CategoryPagedResponse

}