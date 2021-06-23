/*
 * Created by Joseph Dalughut on 13/06/2021, 11:58
 * Copyright (c) 2021 . All rights reserved.
 */

package ng.agrimart.android.view.auth.signup

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.browser.customtabs.CustomTabColorSchemeParams
import androidx.browser.customtabs.CustomTabsIntent
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import ng.agrimart.android.R
import ng.agrimart.android.core.URL_TERMS_AND_CONDITIONS
import ng.agrimart.android.databinding.FragmentSignupPasswordBinding
import ng.agrimart.android.extensions.showSnackbarMessage
import ng.agrimart.android.view.auth.AuthActivity
import ng.agrimart.android.view.main.MainActivity
import ng.agrimart.android.view.popups.AlertDialog

/**
 * A [Fragment] which collects a new users password for completing registration.
 * Use the [SignupPasswordFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class SignupPasswordFragment : Fragment() {

    private lateinit var binding: FragmentSignupPasswordBinding
    private val viewModel: SignupViewModel
    get() { return (activity as AuthActivity).signupViewModel }

    companion object {
        /**
         * Use this factory method to create a new instance [SignupPasswordFragment]
         */
        @JvmStatic
        fun newInstance() = SignupPasswordFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View {
        binding = FragmentSignupPasswordBinding.inflate(inflater)

        attachActions()
        observeModel()
        return binding.root
    }

    private fun attachActions() {
        binding.btnRegister.setOnClickListener {
            viewModel.onSubmitSignupPassword(binding.txtPassword.toString().trim(), binding.txtConfirmPassword.toString().trim())
        }
        binding.btnTermsAndConditions.setOnClickListener {
            viewModel.onClickTermsAndConditionsButton()
        }
        binding.toolbar.setNavigationOnClickListener {
            viewModel.onClickSignupPasswordCloseButton()
        }
    }

    private fun observeModel() {
        viewModel.events.observe(viewLifecycleOwner, {
            when (it) {
                SignupViewModel.Event.EVENT_SIGNUP_BEGAN -> onSignupBegan()
                SignupViewModel.Event.EVENT_SIGNUP_ENDED -> onSignupEnded()
                SignupViewModel.Event.ACTION_NAV_TERMS_AND_CONDITIONS -> navigateToTermsAndConditions()
                SignupViewModel.Event.ACTION_NAV_DASHBOARD -> navigateToDashboard()
                SignupViewModel.Event.ACTION_NAV_LOGIN -> navigateToLogin()
                SignupViewModel.Event.ACTION_NAV_SIGNUP_DETAIL -> navigateToSignupDetail()
            }
        })
        viewModel.alerts.observe(viewLifecycleOwner, {
            when (it) {
                SignupViewModel.Alert.ERROR_INVALID_PASSWORD ->
                    showSnackbarMessage(requireActivity(), binding.root, R.string.error_password_invalid)
                SignupViewModel.Alert.ERROR_SHORT_PASSWORD ->
                    showSnackbarMessage(requireActivity(), binding.root, R.string.error_password_short)
                SignupViewModel.Alert.ERROR_INVALID_PASSWORD_CONFIIRMATION ->
                    showSnackbarMessage(requireActivity(), binding.root, R.string.error_password_confirm_invalid)
                SignupViewModel.Alert.ERROR_MISMATCH_PASSWORD_CONFIIRMATION ->
                    showSnackbarMessage(requireActivity(), binding.root, R.string.error_password_confirm_mismatch)
                SignupViewModel.Alert.ERROR_SIGNUP_FAILED -> showSignupErrorPopup()
            }
        })
    }

    private fun onSignupBegan() {
        binding.btnRegister.startAnimation()
        binding.btnRegister.isClickable = false
        binding.txtPassword.isEnabled = false
        binding.txtConfirmPassword.isEnabled = false
    }

    private fun onSignupEnded() {
        binding.btnRegister.revertAnimation()
        binding.btnRegister.isClickable = true
        binding.txtPassword.isEnabled = true
        binding.txtConfirmPassword.isEnabled = true
    }

    private fun navigateToTermsAndConditions() {
        CustomTabsIntent.Builder()
            .setColorSchemeParams(CustomTabsIntent.COLOR_SCHEME_LIGHT,
                CustomTabColorSchemeParams.Builder()
                    .setToolbarColor(resources.getColor(R.color.agrimart_green))
                    .setSecondaryToolbarColor(Color.WHITE)
                    .build()
            )
            .build().launchUrl(requireContext(), Uri.parse(URL_TERMS_AND_CONDITIONS))
    }

    private fun navigateToLogin() {
        (activity as AuthActivity).attachLoginView()
    }

    private fun navigateToDashboard() {
        val intent = Intent(requireActivity(), MainActivity::class.java)
        startActivity(intent)
        activity?.finish()
    }

    private fun navigateToSignupDetail() {
        (activity as AuthActivity).onBackPressed()
    }

    private fun showSignupErrorPopup() {
        AlertDialog.Builder(requireActivity())
            .title(R.string.error)
            .message(R.string.error_message_generic)
            .icon(R.drawable.ic_alert)
            .positiveButton(R.string.okay, View.OnClickListener {})
            .build().show()
    }


}