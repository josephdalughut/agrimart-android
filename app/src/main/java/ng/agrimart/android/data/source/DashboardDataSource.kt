package ng.agrimart.android.data.source

import ng.agrimart.android.data.api.DashboardApi
import ng.agrimart.android.domain.model.dashboard.DashboardFeedResponse

/**
 * A data-source used to fetch data for the dashboard feature.
 */
interface DashboardDataSource {

    /**
     * Fetches a feed which is displayed on the dashboard to the user.
     * @see DashboardFeedResponse
     */
    suspend fun feed(): DashboardFeedResponse

}

/**
 * A [DashboardDataSource] which interacts with the remote API & data-source.
 * @see DashboardDataSource
 */
class RemoteDashboardDataSource(val dashboardApi: DashboardApi): DashboardDataSource {

    override suspend fun feed(): DashboardFeedResponse {
        return dashboardApi.feed()
    }

}