/*
 * Created by Joseph Dalughut on 16/06/2021, 03:17
 * Copyright (c) 2021 . All rights reserved.
 */

package ng.agrimart.android.view.main.dashboard

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import ng.agrimart.android.databinding.FragmentDashboardBinding
import dagger.hilt.android.AndroidEntryPoint
import ng.agrimart.android.R

/**
 * A [Fragment] which represents the users Dashboard.
 * Use the [DashboardFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class DashboardFragment : Fragment() {

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment.
         *
         * @return A new instance of fragment DashboardFragment.
         */
        @JvmStatic
        fun newInstance() = DashboardFragment()
    }

    lateinit var binding: FragmentDashboardBinding
    private val viewModel: DashboardViewModel by viewModels()
    private var timeChangeBroadcastReceiver: BroadcastReceiver? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        attachTimeBroadcastListener()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentDashboardBinding.inflate(inflater)

        observeModel()
        return binding.root
    }

    private fun observeModel() {
        viewModel.timeOfDay.observe(viewLifecycleOwner, {
            val greetingRes: Int = when (it) {
                DashboardViewModel.TimeOfDay.MORNING -> R.string.dashboard_greeting_good_morning
                DashboardViewModel.TimeOfDay.AFTERNOON -> R.string.dashboard_greeting_good_afternoon
                DashboardViewModel.TimeOfDay.EVENING -> R.string.dashboard_greeting_good_evening
            }
            binding.collapsingToolbar.title = getString(greetingRes)
        })
    }

    private fun attachTimeBroadcastListener() {
        val intentFilter = IntentFilter()
        intentFilter.addAction(Intent.ACTION_TIME_CHANGED)
        intentFilter.addAction(Intent.ACTION_TIME_TICK)
        intentFilter.addAction(Intent.ACTION_TIMEZONE_CHANGED)
        intentFilter.addAction(Intent.ACTION_DATE_CHANGED)

        timeChangeBroadcastReceiver = object: BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                viewModel.refreshTimeOfDay()
            }
        }
        requireActivity().registerReceiver(timeChangeBroadcastReceiver!!, intentFilter)
    }

    private fun detachTimeBroadcastListener() {
        timeChangeBroadcastReceiver?.let {
            requireActivity().unregisterReceiver(it)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        detachTimeBroadcastListener()
    }

}