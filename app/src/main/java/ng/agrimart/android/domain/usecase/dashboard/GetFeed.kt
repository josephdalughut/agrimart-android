package ng.agrimart.android.domain.usecase.dashboard

import ng.agrimart.android.domain.model.dashboard.DashboardFeedResponse
import ng.agrimart.android.domain.repository.DashboardRepository

/**
 * Fetches the users home feed.
 */
class GetFeed(private val dashboardRepository: DashboardRepository) {

    suspend fun execute(page: Int?): DashboardFeedResponse {
        return dashboardRepository.feed(page)
    }

}
