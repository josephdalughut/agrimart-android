/*
 * Created by Joseph Dalughut on 13/06/2021, 08:56
 * Copyright (c) 2021 . All rights reserved.
 */

package ng.agrimart.android.view.landing

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import ng.agrimart.android.R
import ng.agrimart.android.databinding.ActivityLandingPageBinding
import ng.agrimart.android.view.auth.AuthActivity
import java.io.IOException

/**
 * Activity which shows a brief on-boarding to the application
 */
class LandingPageActivity : AppCompatActivity(), MediaPlayer.OnPreparedListener {

    lateinit var binding: ActivityLandingPageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLandingPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        attachActions()
        loadVideo()
    }

    private fun attachActions() {
        binding.btnGetStarted.setOnClickListener {
            navigateToAuth()
        }
        binding.btnClose.setOnClickListener {
            finish()
        }
    }

    private fun loadVideo() {
        try {
            binding.vwVideo.setRawData(R.raw.vegetable_stand)
            binding.vwVideo.isLooping = true
            binding.vwVideo.prepareAsync(this)
        } catch (exception: IOException) {
            Log.d("OnboardingActivity", exception.toString())
            exception.printStackTrace()
        }
    }

    private fun navigateToAuth() {
        val intent = Intent(this, AuthActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onPrepared(mp: MediaPlayer?) {
        binding.vwVideo.start()
    }
}