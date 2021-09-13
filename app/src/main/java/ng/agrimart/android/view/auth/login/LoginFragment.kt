/*
 * Created by Joseph Dalughut on 13/06/2021, 11:58
 * Copyright (c) 2021 . All rights reserved.
 */

package ng.agrimart.android.view.auth.login

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.github.razir.progressbutton.hideProgress
import com.github.razir.progressbutton.showProgress
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import ng.agrimart.android.R
import ng.agrimart.android.databinding.FragmentLoginBinding
import ng.agrimart.android.extensions.hideKeyboard
import ng.agrimart.android.view.auth.AuthActivity
import ng.agrimart.android.view.landing.LandingPageActivity
import ng.agrimart.android.view.main.MainActivity
import ng.agrimart.android.view.passwordReset.PasswordResetActivity
import ng.agrimart.android.view.popups.AlertDialog

/**
 * A [Fragment] which lets the user login to their account.
 * Use the [LoginFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding

    private val viewModel: LoginViewModel by viewModels()

    companion object {
        /**
         * Use this factory method to create a new instance [LoginFragment]
         */
        @JvmStatic
        fun newInstance() = LoginFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View {
        binding = FragmentLoginBinding.inflate(inflater)

        attachActions()
        observeModel()
        return binding.root
    }

    private fun attachActions() {
        binding.btnLogin.setOnClickListener {
            viewModel.onSubmitLoginDetail(binding.txtEmail.text.toString(), binding.txtPassword.text.toString())
        }
        binding.btnSignup.setOnClickListener {
            viewModel.onClickSignup()
        }
        binding.btnForgotPassword.setOnClickListener {
            viewModel.onClickForgotPassword()
        }
        binding.toolbar.setNavigationOnClickListener {
            viewModel.onClickLoginCloseButton()
        }
    }

    private fun observeModel() {
        viewModel.events.observe(viewLifecycleOwner, {
            when (it) {
                LoginViewModel.Event.EVENT_LOGIN_BEGAN -> onLoginBegan()
                LoginViewModel.Event.EVENT_LOGIN_ENDED -> onLoginEnded()
                LoginViewModel.Event.ACTION_NAV_SIGNUP -> navigateToSignup()
                LoginViewModel.Event.ACTION_NAV_DASHBOARD -> navigateToDashboard()
                LoginViewModel.Event.ACTION_NAV_RESET_PASSWORD -> navigateToResetPassword()
                LoginViewModel.Event.ACTION_NAV_CLOSE -> navigateToOnboarding()
            }
        })
        viewModel.alerts.observe(viewLifecycleOwner, {
            when (it) {
                LoginViewModel.Alert.ERROR_INVALID_EMAIL_ADDRESS -> showSnackbarMessage(R.string.error_email_invalid)
                LoginViewModel.Alert.ERROR_INVALID_PASSWORD -> showSnackbarMessage(R.string.error_password_invalid)
                LoginViewModel.Alert.ERROR_LOGIN_FAILED -> showLoginErrorPopup()
            }
        })
    }

    private fun onLoginBegan() {
        binding.btnLogin.showProgress { progressColor = Color.WHITE }
        binding.btnLogin.isEnabled = false
        binding.txtEmail.isEnabled = false
        binding.txtPassword.isEnabled = false
    }

    private fun onLoginEnded() {
        binding.btnLogin.hideProgress()
        binding.btnLogin.isEnabled = true
        binding.txtEmail.isEnabled = true
        binding.txtPassword.isEnabled = true
    }

    private fun navigateToSignup() {
        (activity as AuthActivity).attachSignupDetailFragment()
    }

    private fun navigateToResetPassword() {
        startActivity(Intent(requireActivity(), PasswordResetActivity::class.java))
    }

    private fun navigateToDashboard() {
        startActivity(Intent(requireActivity(), MainActivity::class.java))
        activity?.finish()
    }

    private fun navigateToOnboarding() {
        startActivity(Intent(requireActivity(), LandingPageActivity::class.java))
        activity?.finish()
    }

    private fun showLoginErrorPopup() {
        AlertDialog.Builder(requireActivity())
            .title(R.string.error)
            .message(R.string.error_message_generic)
            .icon(R.drawable.ic_alert)
            .positiveButton(R.string.okay, View.OnClickListener {})
            .build().show()
    }

    private fun showSnackbarMessage(@StringRes message: Int) {
        hideKeyboard(requireActivity())
        Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()
    }

}