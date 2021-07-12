/*
 * Created by Joseph Dalughut on 16/06/2021, 02:46
 * Copyright (c) 2021 . All rights reserved.
 */

package ng.agrimart.android.view.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentStatePagerAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView
import ng.agrimart.android.databinding.ActivityMainBinding
import ng.agrimart.android.view.main.dashboard.DashboardFragment
import ng.agrimart.android.view.reuseable.FragmentAdapter
import dagger.hilt.android.AndroidEntryPoint
import ng.agrimart.android.R
import ng.agrimart.android.view.main.catalog.CatalogFragment

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    lateinit var binding: ActivityMainBinding

    var pagerAdapter: FragmentAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupPages()
        binding.navigation.setOnNavigationItemSelectedListener(this)
    }

    private fun setupPages() {
        val fragments = ArrayList<Fragment>()
        fragments.add(DashboardFragment.newInstance())
        fragments.add(CatalogFragment.newInstance())

        pagerAdapter = FragmentAdapter(supportFragmentManager, FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT)
        pagerAdapter?.fragments = fragments
        binding.pager.adapter = pagerAdapter

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.home -> navigateToMenuPosition(0)
            R.id.catalog -> navigateToMenuPosition(1)
            R.id.cart -> navigateToMenuPosition(2)
            R.id.account -> navigateToMenuPosition(3)
        }
        return true
    }

    private fun navigateToMenuPosition(position: Int) {
        binding.pager.setCurrentItem(position, false)
    }

}