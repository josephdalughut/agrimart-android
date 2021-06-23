/*
 * Created by Joseph Dalughut on 13/06/2021, 11:58
 * Copyright (c) 2021 . All rights reserved.
 */

package ng.agrimart.android.view.passwordReset

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ng.agrimart.android.R
import ng.agrimart.android.databinding.FragmentPasswordResetBinding
import ng.agrimart.android.extensions.showSnackbarMessage
import ng.agrimart.android.view.popups.AlertDialog
import dagger.hilt.android.AndroidEntryPoint

/**
 * A [Fragment] which lets the user register for an account.
 * Use the [PasswordResetFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class PasswordResetFragment : Fragment() {

    private lateinit var binding: FragmentPasswordResetBinding
    private val viewModel: PasswordResetViewModel
        get() {
            return (activity as PasswordResetActivity).viewModel
        }

    companion object {
        /**
         * Use this factory method to create a new instance [PasswordResetFragment]
         */
        @JvmStatic
        fun newInstance() = PasswordResetFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View {
        binding = FragmentPasswordResetBinding.inflate(inflater)

        attachActions()
        observeModel()
        return binding.root
    }

    private fun attachActions() {
        binding.btnSubmit.setOnClickListener {
            viewModel.onSubmitPasswordResetDetail(binding.vwOtp.text.toString().trim(), 
                binding.txtPassword.text.toString(), 
                binding.txtConfirmPassword.text.toString())
        }
        binding.toolbar.setNavigationOnClickListener {
            viewModel.onClickCloseResetPasswordButton()
        }
    }

    private fun observeModel() {
        viewModel.events.observe(viewLifecycleOwner, {
            when (it) {
                PasswordResetViewModel.Event.RESET_BEGAN -> onResetBegan()
                PasswordResetViewModel.Event.RESET_ENDED -> onResetEnded()
                PasswordResetViewModel.Event.ACTION_NAV_REQUEST_RESET -> navigateToRequestPasswordReset()
            }
        })
        viewModel.currentResetEmail.observe(viewLifecycleOwner, { userEmail ->
            binding.txtDescription.setText(resources.getString(R.string.reset_password_desc, userEmail))
        })
        viewModel.alerts.observe(viewLifecycleOwner, {
            when (it) {
                PasswordResetViewModel.Alert.ERROR_OTP_INVALID ->
                    showSnackbarMessage(requireActivity(), binding.root, R.string.error_otp_invalid)
                PasswordResetViewModel.Alert.ERROR_INVALID_PASSWORD ->
                    showSnackbarMessage(requireActivity(), binding.root, R.string.error_password_invalid)
                PasswordResetViewModel.Alert.ERROR_SHORT_PASSWORD ->
                    showSnackbarMessage(requireActivity(), binding.root, R.string.error_password_short)
                PasswordResetViewModel.Alert.ERROR_INVALID_PASSWORD_CONFIIRMATION ->
                    showSnackbarMessage(requireActivity(), binding.root, R.string.error_password_confirm_invalid)
                PasswordResetViewModel.Alert.ERROR_MISMATCH_PASSWORD_CONFIIRMATION ->
                    showSnackbarMessage(requireActivity(), binding.root, R.string.error_password_confirm_mismatch)
                PasswordResetViewModel.Alert.RESET_ERROR -> showResetErrorPopup()
                PasswordResetViewModel.Alert.RESET_SUCCESS -> showResetSuccessPopup()

            }
        })
    }

    private fun onResetBegan() {
        binding.btnSubmit.startAnimation()
        binding.btnSubmit.isClickable = false
        binding.vwOtp.isEnabled = false
        binding.txtPassword.isEnabled = false
        binding.txtConfirmPassword.isEnabled = false
    }

    private fun onResetEnded() {
        binding.btnSubmit.revertAnimation()
        binding.btnSubmit.isClickable = true
        binding.vwOtp.isEnabled = true
        binding.txtPassword.isEnabled = true
        binding.txtConfirmPassword.isEnabled = true
    }

    private fun navigateToRequestPasswordReset() {
        (activity as PasswordResetActivity).attachRequestPasswordResetView()
    }

    private fun showResetErrorPopup() {
        AlertDialog.Builder(requireActivity())
            .title(R.string.error)
            .message(R.string.error_message_generic)
            .icon(R.drawable.ic_alert)
            .positiveButton(R.string.okay, View.OnClickListener {})
            .build().show()
    }

    private fun showResetSuccessPopup() {
        AlertDialog.Builder(requireActivity())
            .title(R.string.password_changed)
            .message(R.string.password_changed_desc)
            .cancelable(false)
            .positiveButton(R.string.back_to_login, View.OnClickListener {
                activity?.finish()
            })
            .build().show()
    }


}