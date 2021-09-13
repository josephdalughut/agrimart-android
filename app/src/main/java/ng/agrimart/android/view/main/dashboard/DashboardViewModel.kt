/*
 * Created by Joseph Dalughut on 22/06/2021, 00:29
 * Copyright (c) 2021 . All rights reserved.
 */

package ng.agrimart.android.view.main.dashboard

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import dagger.hilt.android.lifecycle.HiltViewModel
import ng.agrimart.android.domain.repository.CategoryRepository
import ng.agrimart.android.domain.repository.ProductRepository
import ng.agrimart.android.view.main.dashboard.data.DashboardFeedDataSource
import ng.agrimart.android.view.util.ContentLoaderCallbacks
import java.util.*
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    productRepository: ProductRepository,
    categoryRepository: CategoryRepository
): ViewModel(), ContentLoaderCallbacks {

    companion object {
        val LOG_TAG: String = DashboardViewModel::class.java.simpleName
    }

    val timeOfDayData = MutableLiveData<TimeOfDay>()
    val placeholderVisibility = MutableLiveData<LoadingPlaceholderVisibility>()

    private val feedItemsDataSource = DashboardFeedDataSource(
        productRepository = productRepository,
        categoryRepository = categoryRepository,
        loaderCallbacks = this
    )

    var feedItemsData = Pager(
        PagingConfig(pageSize = 20, enablePlaceholders = true)
    ) {
        feedItemsDataSource
    }.flow.asLiveData()

    init {
        refreshTimeOfDay()
    }

    /**
     * Refreshes the time of day shown on the dashboard.
     */
    fun refreshTimeOfDay() {
        val c: Calendar = Calendar.getInstance()
        val timeOfDay: TimeOfDay?
        val hourOfDay: Int = c.get(Calendar.HOUR_OF_DAY)
        timeOfDay = when (hourOfDay) {
            in 0..11 -> {
                TimeOfDay.MORNING
            }
            in 12..15 -> {
                TimeOfDay.AFTERNOON
            }
            else -> {
                TimeOfDay.EVENING
            }
        }
        this.timeOfDayData.postValue(timeOfDay)
    }

    /**
     * Requests a refresh of the contents of this view, usually from an external source.
     */
    fun refreshContent() {
        feedItemsDataSource.invalidate()
    }

    override fun onContentLoadBegan() {
        Log.d(LOG_TAG, "Content load began")
        placeholderVisibility.postValue(LoadingPlaceholderVisibility.LOADING_BEGAN)
    }

    override fun onContentLoadSuccess() {
        Log.d(LOG_TAG, "Content load success")
        placeholderVisibility.postValue(LoadingPlaceholderVisibility.SUCCESS)
    }

    override fun onContentLoadMore() {
        super.onContentLoadMore()
        Log.d(LOG_TAG, "Content loading more")
    }

    override fun onContentLoadError(exception: Exception) {
        Log.d(LOG_TAG, "Content load error")
        placeholderVisibility.postValue(LoadingPlaceholderVisibility.ERROR)
    }

    /**
     * Represents the time of day. This is used to display the user-greeting on the dashboard.
     */
    enum class TimeOfDay {
        MORNING,
        AFTERNOON,
        EVENING
    }

    /**
     * Represents the view-state of content loading.
     */
    enum class LoadingPlaceholderVisibility {
        LOADING_BEGAN,
        LOADING_MORE_BEGAN,
        SUCCESS,
        ERROR
    }

}