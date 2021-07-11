package ng.agrimart.android.hilt

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ng.agrimart.android.data.api.CategoryApi
import ng.agrimart.android.data.api.ProductApi
import ng.agrimart.android.data.api.RetrofitApiHelper
import ng.agrimart.android.domain.api.AuthApi
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Provides
    @Singleton
    fun provideAuthApi(retrofitApiHelper: RetrofitApiHelper): AuthApi = retrofitApiHelper.getAuthApi(true)

    @Provides
    @Singleton
    fun provideProductApi(retrofitApiHelper: RetrofitApiHelper): ProductApi = retrofitApiHelper.getProductApi()

    @Provides
    @Singleton
    fun provideCategoryApi(retrofitApiHelper: RetrofitApiHelper): CategoryApi = retrofitApiHelper.getCategoryApi()

}