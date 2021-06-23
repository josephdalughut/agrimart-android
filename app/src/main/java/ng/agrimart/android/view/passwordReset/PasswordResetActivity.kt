/*
 * Created by Joseph Dalughut on 5/4/21 5:22 PM
 * Copyright (c) 2021 . All rights reserved.
 */

package ng.agrimart.android.view.passwordReset

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import ng.agrimart.android.R
import ng.agrimart.android.databinding.ActivityForgotPasswordBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PasswordResetActivity : AppCompatActivity() {

    val viewModel: PasswordResetViewModel by viewModels()
    lateinit var binding: ActivityForgotPasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgotPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        attachRequestPasswordResetView()
    }

    fun attachRequestPasswordResetView() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.vwFragments, RequestPasswordResetFragment.newInstance())
            .commitAllowingStateLoss()
    }

    fun attachResetPasswordView() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.vwFragments, PasswordResetFragment.newInstance())
            .addToBackStack("")
            .commitAllowingStateLoss()
    }

}