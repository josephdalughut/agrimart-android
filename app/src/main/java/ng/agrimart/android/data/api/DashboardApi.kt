package ng.agrimart.android.data.api

import ng.agrimart.android.domain.model.dashboard.DashboardFeedResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface DashboardApi {

    @GET("dashboard")
    suspend fun feed(@Query("page") page: Int?): DashboardFeedResponse

}