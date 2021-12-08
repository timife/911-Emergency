package com.timife.a911.createProfile.ui

import android.content.Context
import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.snackbar.Snackbar
import com.timife.a911.EmergencyApplication
import com.timife.a911.R
import com.timife.a911.databinding.FragmentCreateProfileBinding
import com.timife.a911.extensions.disableClick
import com.timife.a911.extensions.enableClick
import com.timife.a911.utils.EventObserver
import com.timife.a911.utils.Messages
import com.timife.a911.utils.ValidationStates
import com.timife.a911.utils.states.AuthResult
import javax.inject.Inject

/**
 * Created on 08/12/2021
 */
class CreateProfileFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentCreateProfileBinding

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel by viewModels<CreateProfileViewModel> { viewModelFactory }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCreateProfileBinding.inflate(inflater)
        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as EmergencyApplication).emergencyComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.saveProfile.setOnClickListener {
            val email = binding.profileEmail.text.toString().trim()
            val profileName = binding.profileName.text.toString().trim()
            val password = binding.password.text.toString().trim()
            val confirmPassword = binding.confirmPassword.text.toString().trim()

            validateAndCreate(
                profileName, email, password, confirmPassword
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
        createProfile(profileName,email,password,confirmPassword)
    }

    viewModel.showSnackBar.observe(viewLifecycleOwner, EventObserver {
        Snackbar.make(binding.createProfileConstraint, it, Snackbar.LENGTH_LONG).show()
    })
}

private fun createProfile(profileName: String,email: String,password: String,confirmPassword: String) {
    viewModel.registerNewUser(
        profileName,
        email,
        password,
        confirmPassword
    ).observe(viewLifecycleOwner, Observer { result ->
        when (result) {
            is AuthResult.Loading -> {
                binding.progressBar.visibility =View.VISIBLE
                binding.saveProfileButton.disableClick()
            }

            is AuthResult.Failed -> {
                viewModel.snackBarMessage(result.data)
                binding.progressBar.visibility =View.GONE
                binding.saveProfileButton.enableClick()
                Toast.makeText(requireContext(),result.data,Toast.LENGTH_SHORT).show()
            }

            is AuthResult.Success -> {
                binding.progressBar.visibility =View.GONE
                binding.saveProfileButton.enableClick()
//                findNavController().navigateUp()
                Toast.makeText(requireContext(),result.data,Toast.LENGTH_SHORT).show()
                dismiss()
            }
            else -> {/* no-op */
            }
        }
    })

}
override fun getTheme(): Int {
    return R.style.ProfileBottomSheetDialogFragment
}
}