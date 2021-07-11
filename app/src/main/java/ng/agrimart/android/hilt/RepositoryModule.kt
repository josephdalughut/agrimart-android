package ng.agrimart.android.hilt

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ng.agrimart.android.data.api.CategoryApi
import ng.agrimart.android.data.api.ProductApi
import ng.agrimart.android.data.repository.CategoryDataRepository
import ng.agrimart.android.data.repository.ProductDataRepository
import ng.agrimart.android.domain.repository.CategoryRepository
import ng.agrimart.android.domain.repository.ProductRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideDashboardRepository(productApi: ProductApi): ProductRepository = ProductDataRepository(productApi)

    @Provides
    @Singleton
    fun provideCategoryRepository(categoryApi: CategoryApi): CategoryRepository = CategoryDataRepository(categoryApi)

}