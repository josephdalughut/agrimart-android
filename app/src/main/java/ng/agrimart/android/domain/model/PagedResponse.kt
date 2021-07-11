package ng.agrimart.android.domain.model

/**
 * Base-class for API-responses which offer pagination.
 */
interface PagedResponse<T> {

    var currentPage: Int
    var data: T

}