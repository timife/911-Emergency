package com.timife.a911.ui.auth.signup

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.timife.a911.EmergencyApplication
import com.timife.a911.R
import com.timife.a911.databinding.FragmentSignupBinding
import com.timife.a911.extensions.disableClick
import com.timife.a911.extensions.enableClick
import com.timife.a911.extensions.navigateSafe
import com.timife.a911.ui.BaseFragment
import com.timife.a911.utils.EventObserver
import com.timife.a911.utils.Messages
import com.timife.a911.utils.ValidationStates
import com.timife.a911.utils.states.AuthResult
import javax.inject.Inject

class SignupFragment : BaseFragment() {
    private lateinit var binding : FragmentSignupBinding

    private val signUpViewModel by viewModels<SignupViewModel>{
        viewModelFactoryProvider
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as EmergencyApplication).emergencyComponent.inject(this)
    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignupBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.signupText.setOnClickListener {
            val email = binding.profileEmail.text.toString().trim()
            val profileName = binding.profileName.text.toString().trim()
            val password = binding.password.text.toString().trim()
            val confirmPassword = binding.confirmPassword.text.toString().trim()

            validateAndCreate(
                profileName, email, password, confirmPassword
            )
        }

        binding.backToLogin.setOnClickListener {
            navigateSafe(
                SignupFragmentDirections.actionGlobalAuthNavigation()
            )
        }
    }

    private fun validateAndCreate(
        profileName: String,
        email: String,
        password: String,
        confirmPassword: String
    ){
        if (profileName.isEmpty()) {
            binding.profileName.error = Messages.BLANK_FIELDS_IN_FORM
            binding.profileName.requestFocus()
        } else if (email.isEmpty()) {
            binding.profileEmail.error = Messages.BLANK_FIELDS_IN_FORM
            binding.profileEmail.requestFocus()
        }else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.profileEmail.error = "Please provide valid email!"
            binding.profileEmail.requestFocus()
        } else if (password.isEmpty()) {
            binding.password.error = Messages.BLANK_FIELDS_IN_FORM
            binding.password.requestFocus()
        } else if (password.length < 6) {
            binding.password.error = Messages.SHORT_PASSWORD
            binding.password.requestFocus()
        }else if(confirmPassword.isEmpty()){
            binding.confirmPassword.error = Messages.BLANK_FIELDS_IN_FORM
        } else if (password != confirmPassword) {
            ValidationStates.Error(Messages.MISMATCH_PASSWORD)
        } else {
            signUp(profileName,email,password,confirmPassword)
        }

        signUpViewModel.showSnackBar.observe(viewLifecycleOwner, EventObserver {
            Snackbar.make(binding.signupConstraint, it, Snackbar.LENGTH_LONG).show()
        })
    }

    private fun signUp(profileName: String,email: String,password: String,confirmPassword: String) {
        signUpViewModel.registerNewUser(
            profileName,
            email,
            password,
            confirmPassword
        ).observe(viewLifecycleOwner,  { result ->
            when (result) {
                is AuthResult.Loading -> {
                    binding.progressSignUp.visibility =View.VISIBLE
                    binding.saveProfileButton.disableClick()
                }

                is AuthResult.Failed -> {
                    signUpViewModel.snackBarMessage(result.data)
                    binding.progressSignUp.visibility =View.GONE
                    binding.saveProfileButton.enableClick()
                    Toast.makeText(requireContext(),result.data, Toast.LENGTH_SHORT).show()
                }

                is AuthResult.Success -> {
                    binding.progressSignUp.visibility =View.GONE
                    binding.saveProfileButton.enableClick()
                    Toast.makeText(requireContext(),result.data, Toast.LENGTH_SHORT).show()
                    findNavController().navigateUp()
                }
                else -> {/* no-op */
                }
            }
        })

    }



}