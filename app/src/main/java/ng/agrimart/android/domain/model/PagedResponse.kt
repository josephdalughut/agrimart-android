package ng.agrimart.android.domain.model

import ng.agrimart.android.data.model.Category
import ng.agrimart.android.data.model.Product

/**
 * Base-class for API-responses which offer pagination.
 */
interface PagedResponse<T> {

    var currentPage: Int
    var data: List<T>

}

data class ProductPagedResponse(override var currentPage: Int, override var data: List<Product>)
    : PagedResponse<Product>

data class CategoryPagedResponse(override var currentPage: Int, override var data: List<Category>)
    : PagedResponse<Category>