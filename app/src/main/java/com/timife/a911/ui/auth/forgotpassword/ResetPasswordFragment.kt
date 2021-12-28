package com.timife.a911.ui.auth.forgotpassword

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.timife.a911.EmergencyApplication
import com.timife.a911.R
import com.timife.a911.databinding.FragmentResetPasswordBinding
import com.timife.a911.extensions.disableClick
import com.timife.a911.extensions.enableClick
import com.timife.a911.ui.BaseFragment
import com.timife.a911.utils.states.DataState

class ResetPasswordFragment : BaseFragment() {
    private lateinit var binding : FragmentResetPasswordBinding
    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as EmergencyApplication).emergencyComponent.inject(this)
    }


    private val  resetPasswordViewModel by viewModels<ResetPasswordViewModel> { viewModelFactoryProvider }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentResetPasswordBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.goBackToLogin.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.sendReset.setOnClickListener {
            val email = binding.resetEmail.text.toString().trim()
            if (!TextUtils.isEmpty(email)) {
                resetPassword(email)
            }
        }
    }

    private fun resetPassword(email:String){
        resetPasswordViewModel.resetPassword(email).observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is DataState.Loading -> {
                    binding.progressReset.visibility = View.VISIBLE
                    binding.resetButton.disableClick()
                }

                is DataState.Success<*> -> {
                    binding.progressReset.visibility = View.GONE
                    binding.resetButton.enableClick()
                    findNavController().navigateUp()
                }

                is DataState.Error -> {
                    binding.progressReset.visibility = View.GONE
                    binding.resetButton.enableClick()
                    showSnackBar(result.message)
                }
            }
        })
    }

    private fun showSnackBar(message: String) {
        Snackbar.make(binding.resetCoordinator, message, Snackbar.LENGTH_SHORT).show()
    }
}