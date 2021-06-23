/*
 * Created by Joseph Dalughut on 13/06/2021, 11:58
 * Copyright (c) 2021 . All rights reserved.
 */

package ng.agrimart.android.view.auth.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import ng.agrimart.android.R
import ng.agrimart.android.databinding.FragmentSignupDetailBinding
import ng.agrimart.android.extensions.showSnackbarMessage
import ng.agrimart.android.view.auth.AuthActivity

/**
 * A [Fragment] which collects the users details for registering a new account.
 * Use the [SignupDetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class SignupDetailFragment : Fragment() {

    private lateinit var binding: FragmentSignupDetailBinding
    private val viewModel: SignupViewModel
        get() { return (activity as AuthActivity).signupViewModel }

    companion object {
        /**
         * Use this factory method to create a new instance [SignupDetailFragment]
         */
        @JvmStatic
        fun newInstance() = SignupDetailFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View {
        binding = FragmentSignupDetailBinding.inflate(inflater)

        attachActions()
        observeModel()
        return binding.root
    }

    private fun attachActions() {
        binding.toolbar.setNavigationOnClickListener {
            viewModel.onClickSignupDetailCloseButton()
        }
        binding.btnContinue.setOnClickListener {
            viewModel.onSubmitSignupDetail(binding.txtFullName.text.toString().trim(),
                binding.txtEmail.text.toString().trim())
        }
        binding.btnLogin.setOnClickListener {
            viewModel.onClickLoginButton()
        }
    }

    private fun observeModel() {
        viewModel.events.observe(viewLifecycleOwner, {
            when (it) {
                SignupViewModel.Event.ACTION_NAV_SIGNUP_PASSWORD -> navigateToPassword()
                SignupViewModel.Event.ACTION_NAV_LOGIN -> navigateToLogin()
            }
        })
        viewModel.alerts.observe(viewLifecycleOwner, {
            when (it) {
                SignupViewModel.Alert.ERROR_INVALID_FULL_NAME ->
                    showSnackbarMessage(requireActivity(), binding.root, R.string.error_fullname_invalid)
                SignupViewModel.Alert.ERROR_INVALID_EMAIL_ADDRESS ->
                    showSnackbarMessage(requireActivity(), binding.root, R.string.error_email_invalid)
            }
        })
    }

    private fun navigateToLogin() {
        (activity as AuthActivity).supportFragmentManager.popBackStack()
    }

    private fun navigateToPassword() {
        (activity as AuthActivity).attachSignupPasswordFragment()
    }

}