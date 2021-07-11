/*
 * Created by Joseph Dalughut on 22/06/2021, 00:29
 * Copyright (c) 2021 . All rights reserved.
 */

package ng.agrimart.android.view.main.dashboard

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import ng.agrimart.android.domain.repository.DashboardRepository
import ng.agrimart.android.domain.usecase.dashboard.GetFeed
import ng.agrimart.android.view.main.dashboard.data.DashboardFeedDataSource
import ng.agrimart.android.view.main.dashboard.data.DashboardFeedItem
import java.util.*
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val dashboardRepository: DashboardRepository
): ViewModel() {

    val timeOfDayData = MutableLiveData<TimeOfDay>()
    private val feedItemsDataSource = DashboardFeedDataSource(dashboardRepository = dashboardRepository)
    var feedItemsData = Pager<Int, DashboardFeedItem>(
        PagingConfig(pageSize = 20, enablePlaceholders = true)
    ) {
        feedItemsDataSource
    }.flow.asLiveData()

    init {
        refreshTimeOfDay()
    }

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
     * Represents the time of day. This is used to display the user-greeting on the dashboard.
     */
    enum class TimeOfDay {
        MORNING,
        AFTERNOON,
        EVENING
    }

}