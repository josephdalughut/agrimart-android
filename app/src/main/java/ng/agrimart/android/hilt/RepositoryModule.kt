package ng.agrimart.android.hilt

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ng.agrimart.android.data.api.DashboardApi
import ng.agrimart.android.data.repository.DashboardDataRepository
import ng.agrimart.android.domain.repository.DashboardRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideDashboardRepository(dashboardApi: DashboardApi): DashboardRepository = DashboardDataRepository(dashboardApi)

}