/*
 * Created by Joseph Dalughut on 22/06/2021, 00:29
 * Copyright (c) 2021 . All rights reserved.
 */

package ng.agrimart.android.view.main.catalog

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import dagger.hilt.android.lifecycle.HiltViewModel
import ng.agrimart.android.domain.repository.CategoryRepository
import ng.agrimart.android.view.main.catalog.data.CatalogCategoryDataSource
import javax.inject.Inject

@HiltViewModel
class CatalogViewModel @Inject constructor(
    private val categoryRepository: CategoryRepository
): ViewModel() {

    private val categoryDataSource = CatalogCategoryDataSource(
        categoryRepository = categoryRepository)

    var categoryData = Pager(
        PagingConfig(pageSize = 20, enablePlaceholders = true)
    ) {
        categoryDataSource
    }.flow.asLiveData()

}