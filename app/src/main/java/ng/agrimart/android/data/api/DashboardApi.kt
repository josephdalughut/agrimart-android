package ng.agrimart.android.data.api

import ng.agrimart.android.domain.model.dashboard.DashboardFeedResponse
import retrofit2.http.GET

interface DashboardApi {

    @GET("dashboard")
    suspend fun feed(): DashboardFeedResponse

}