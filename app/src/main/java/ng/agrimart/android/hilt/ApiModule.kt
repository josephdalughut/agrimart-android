package ng.agrimart.android.hilt

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ng.agrimart.android.data.api.DashboardApi
import ng.agrimart.android.data.api.RetrofitApiHelper
import ng.agrimart.android.data.repository.DashboardDataRepository
import ng.agrimart.android.domain.api.AuthApi
import ng.agrimart.android.domain.repository.DashboardRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Provides
    @Singleton
    fun provideAuthApi(retrofitApiHelper: RetrofitApiHelper): AuthApi = retrofitApiHelper.getAuthApi(true)

    @Provides
    @Singleton
    fun provideDashboardApi(retrofitApiHelper: RetrofitApiHelper): DashboardApi = retrofitApiHelper.getDashboardApi()

}