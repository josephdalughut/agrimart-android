package ng.agrimart.android.data.api

import ng.agrimart.android.domain.model.ProductPagedResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ProductApi {

    @GET("products")
    suspend fun list(@Query("page") page: Int?):  ProductPagedResponse

}