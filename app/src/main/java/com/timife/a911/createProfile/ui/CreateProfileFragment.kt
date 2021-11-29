package com.timife.a911.createProfile.ui

import android.content.Context
import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.auth.FirebaseAuth
import com.timife.a911.EmergencyApplication
import com.timife.a911.R
import com.timife.a911.databinding.FragmentCreateProfileBinding

class CreateProfileFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentCreateProfileBinding
    private lateinit var auth: FirebaseAuth


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding  = FragmentCreateProfileBinding.inflate(inflater)
        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as EmergencyApplication).emergencyComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.saveProfile.setOnClickListener {
            createProfile()
        }

    }
    private fun createProfile(){
        val email = binding.profileEmail.text.toString().trim()
        val profileName = binding.profileName.text.toString().trim()
        val password = binding.password.text.toString().trim()

        if(profileName.isEmpty()){
            binding.profileName.error = "Full name is required!"
            binding.profileName.requestFocus()
            return
        }
        if(email.isEmpty()){
            binding.profileEmail.error = "Email is required!"
            binding.profileEmail.requestFocus()
            return
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            binding.profileEmail.error = "Please provide valid email!"
            binding.profileEmail.requestFocus()
            return
        }
        if (password.isEmpty()){
            binding.password.error = "Password is required!"
            binding.password.requestFocus()
            return
        }
        if (password.length < 6){
            binding.password.error = "Minimum password length should be 6 characters"
            binding.password.requestFocus()
            return
        }
    }
    override fun getTheme(): Int {
        return R.style.ProfileBottomSheetDialogFragment
    }


}