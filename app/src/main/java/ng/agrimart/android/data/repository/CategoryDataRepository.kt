package ng.agrimart.android.data.repository

import ng.agrimart.android.data.api.CategoryApi
import ng.agrimart.android.data.source.CategoryDataSource
import ng.agrimart.android.data.source.RemoteCategoryDataSource
import ng.agrimart.android.domain.model.CategoryPagedResponse
import ng.agrimart.android.domain.repository.CategoryRepository

class CategoryDataRepository constructor(categoryApi: CategoryApi): CategoryRepository {

    private val remoteDataSource: CategoryDataSource = RemoteCategoryDataSource(categoryApi)

    override suspend fun list(page: Int?, limit: Int?, order: String?): CategoryPagedResponse {
        return remoteDataSource.list(page, limit, order)
    }

}