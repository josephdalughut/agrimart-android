package ng.agrimart.android.data.repository

import ng.agrimart.android.data.api.DashboardApi
import ng.agrimart.android.data.source.DashboardDataSource
import ng.agrimart.android.data.source.RemoteDashboardDataSource
import ng.agrimart.android.domain.model.dashboard.DashboardFeedResponse
import ng.agrimart.android.domain.repository.DashboardRepository

class DashboardDataRepository constructor(val dashboardApi: DashboardApi): DashboardRepository {

    val remoteDataSource: DashboardDataSource = RemoteDashboardDataSource(dashboardApi)

    override suspend fun feed(page: Int?): DashboardFeedResponse {
        return remoteDataSource.feed(page)
    }

}