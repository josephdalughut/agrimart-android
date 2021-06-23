/*
 * Created by Joseph Dalughut on 13/06/2021, 11:58
 * Copyright (c) 2021 . All rights reserved.
 */

package ng.agrimart.android.view.passwordReset

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ng.agrimart.android.R
import ng.agrimart.android.view.popups.AlertDialog
import dagger.hilt.android.AndroidEntryPoint
import ng.agrimart.android.databinding.FragmentRequestPasswordResetBinding
import ng.agrimart.android.extensions.showSnackbarMessage

/**
 * A [Fragment] which lets the user request a password-reset for their account.
 * Use the [RequestPasswordResetFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class RequestPasswordResetFragment : Fragment() {

    private lateinit var binding: FragmentRequestPasswordResetBinding
    private val viewModel: PasswordResetViewModel
    get() {
        return (activity as PasswordResetActivity).viewModel
    }

    companion object {
        /**
         * Use this factory method to create a new instance [RequestPasswordResetFragment]
         */
        @JvmStatic
        fun newInstance() = RequestPasswordResetFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View {
        binding = FragmentRequestPasswordResetBinding.inflate(inflater)

        attachActions()
        observeModel()
        return binding.root
    }

    private fun attachActions() {
        binding.btnSubmit.setOnClickListener {
            viewModel.onSubmitUserEmail(binding.txtEmail.text.toString())
        }
        binding.toolbar.setNavigationOnClickListener {
            viewModel.onClickCloseRequestResetButton()
        }
    }

    private fun observeModel() {
        viewModel.events.observe(viewLifecycleOwner, {
            when (it) {
                PasswordResetViewModel.Event.REQUEST_RESET_BEGAN -> onRequestResetBegan()
                PasswordResetViewModel.Event.REQUEST_RESET_ENDED -> onRequestResetEnded()
                PasswordResetViewModel.Event.ACTION_NAV_CLOSE -> activity?.finish()
                PasswordResetViewModel.Event.ACTION_NAV_PASSWORD_RESET -> navigateToPasswordReset()
            }
        })
        viewModel.alerts.observe(viewLifecycleOwner, {
            when (it) {
                PasswordResetViewModel.Alert.ERROR_INVALID_EMAIL_ADDRESS ->
                    showSnackbarMessage(requireActivity(), binding.root, R.string.error_email_invalid)
                PasswordResetViewModel.Alert.REQUEST_RESET_ERROR -> showRequestResetPopupFailed()
            }
        })
    }

    private fun onRequestResetBegan() {
        binding.btnSubmit.startAnimation()
        binding.btnSubmit.isClickable = false
        binding.txtEmail.isEnabled = false
    }

    private fun onRequestResetEnded() {
        binding.btnSubmit.revertAnimation()
        binding.btnSubmit.isClickable = true
        binding.txtEmail.isEnabled = true
    }

    private fun navigateToPasswordReset() {
        (activity as PasswordResetActivity).attachResetPasswordView()
    }

    private fun showRequestResetPopupFailed() {
        AlertDialog.Builder(requireActivity())
            .title(R.string.error)
            .message(R.string.error_message_generic)
            .icon(R.drawable.ic_alert)
            .positiveButton(R.string.okay, View.OnClickListener {

            }).build().show()
    }

}