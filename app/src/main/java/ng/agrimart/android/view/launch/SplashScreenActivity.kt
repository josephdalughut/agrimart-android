/*
 * Created by Joseph Dalughut on 5/4/21 5:22 PM
 * Copyright (c) 2021 . All rights reserved.
 */

package ng.agrimart.android.view.launch

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import ng.agrimart.android.databinding.ActivitySplashScreenBinding
import ng.agrimart.android.view.landing.LandingPageActivity
import ng.agrimart.android.view.main.MainActivity

/**
 * A simple Splash-screen. This screen is the main entry-point of the applications UI, and is
 * responsible for checking for the users login-status, and redirecting them to the next screen as
 * necessary.
 */
@AndroidEntryPoint
class SplashScreenActivity : AppCompatActivity() {

    val viewModel: SplashScreenViewModel by viewModels()

    private lateinit var binding: ActivitySplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        observeModel()
    }

    private fun observeModel() {
        viewModel.init()
        viewModel.navigationEvents.observe(this, {
            it?.let {
                when (it) {
                    SplashScreenEvent.NAV_ONBOARDING -> navigateToOnboarding()
                    SplashScreenEvent.NAV_DASHBOARD -> navigateToDashboard()
                }
            }
        })
    }

    private fun navigateToOnboarding() {
        val intent = Intent(this, LandingPageActivity::class.java)
        binding.root.postDelayed({
            startActivity(intent)
            finish()
        }, 1000)
    }

    private fun navigateToDashboard() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

}