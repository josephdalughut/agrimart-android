package ng.agrimart.android.domain.repository

import ng.agrimart.android.domain.model.dashboard.DashboardFeedResponse

/**
 * A repository used to access resources for the users Dashboard.
 */
interface DashboardRepository {

    /**
     * Fetches the data used to populate the users dashboard.
     */
    suspend fun feed(page: Int?): DashboardFeedResponse

}