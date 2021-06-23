/*
 * Created by Joseph Dalughut on 22/06/2021, 00:29
 * Copyright (c) 2021 . All rights reserved.
 */

package ng.agrimart.android.view.main.dashboard

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.*
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(): ViewModel() {

    val timeOfDay = MutableLiveData<TimeOfDay>()

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
        this.timeOfDay.postValue(timeOfDay)
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