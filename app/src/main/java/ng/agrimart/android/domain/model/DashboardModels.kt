package ng.agrimart.android.domain.model.dashboard

import ng.agrimart.android.data.model.Category
import ng.agrimart.android.data.model.Product
import ng.agrimart.android.domain.model.PagedResponse


data class DashboardFeedResponse(override var currentPage: Int,
                                 override var data: List<Product>,
                                 var categories: List<Category>?
): PagedResponse<List<Product>>