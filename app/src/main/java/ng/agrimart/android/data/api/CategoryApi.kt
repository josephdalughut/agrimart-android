package ng.agrimart.android.data.api

import ng.agrimart.android.domain.model.CategoryPagedResponse
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.*

interface CategoryApi {

    @GET("categories")
    suspend fun list(@Query("page") page: Int?,
                     @Query("limit") limit: Int?,
                     @Query("order") order: String?):
            CategoryPagedResponse

}