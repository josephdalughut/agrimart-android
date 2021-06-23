/*
 * Created by Joseph Dalughut on 16/06/2021, 02:46
 * Copyright (c) 2021 . All rights reserved.
 */

package ng.agrimart.android.view.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentStatePagerAdapter
import ng.agrimart.android.databinding.ActivityMainBinding
import ng.agrimart.android.view.main.dashboard.DashboardFragment
import ng.agrimart.android.view.reuseable.FragmentAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    var pagerAdapter: FragmentAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupPages()
    }

    private fun setupPages() {
        val fragments = ArrayList<Fragment>()
        fragments.add(DashboardFragment.newInstance())

        pagerAdapter = FragmentAdapter(supportFragmentManager, FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT)
        pagerAdapter?.fragments = fragments
        binding.pager.adapter = pagerAdapter

    }

}