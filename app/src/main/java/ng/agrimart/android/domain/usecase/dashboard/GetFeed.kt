package ng.agrimart.android.domain.usecase.dashboard

import ng.agrimart.android.domain.model.dashboard.DashboardFeedResponse
import ng.agrimart.android.domain.repository.DashboardRepository

/**
 * Fetches the users home feed.
 */
class GetFeed(val dashboardRepository: DashboardRepository) {

    suspend fun execute(): DashboardFeedResponse {
        return dashboardRepository.feed()
    }

}
