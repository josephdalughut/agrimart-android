/*
 * Created by Joseph Dalughut on 5/4/21 5:22 PM
 * Copyright (c) 2021 . All rights reserved.
 */

package ng.agrimart.android.view.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import ng.agrimart.android.R
import ng.agrimart.android.databinding.ActivityAuthBinding
import ng.agrimart.android.view.auth.login.LoginFragment
import ng.agrimart.android.view.auth.signup.SignupDetailFragment
import dagger.hilt.android.AndroidEntryPoint
import ng.agrimart.android.view.auth.signup.SignupPasswordFragment
import ng.agrimart.android.view.auth.signup.SignupViewModel

@AndroidEntryPoint
class AuthActivity : AppCompatActivity() {

    lateinit var binding: ActivityAuthBinding
    val signupViewModel: SignupViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        attachLoginView()
    }

    fun attachLoginView() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.vwFragments, LoginFragment.newInstance())
            .commitAllowingStateLoss()
    }

    fun attachSignupDetailFragment() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.vwFragments, SignupDetailFragment.newInstance())
            .addToBackStack("")
            .commitAllowingStateLoss()
    }

    fun attachSignupPasswordFragment() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.vwFragments, SignupPasswordFragment.newInstance())
            .addToBackStack("")
            .commitAllowingStateLoss()
    }

}