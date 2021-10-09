package com.timife.a911.createProfile.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.timife.a911.EmergencyApplication
import com.timife.a911.databinding.FragmentCreateProfileBinding

class CreateProfileFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentCreateProfileBinding


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


}