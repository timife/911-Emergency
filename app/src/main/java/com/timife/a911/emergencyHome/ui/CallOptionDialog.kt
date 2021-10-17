package com.timife.a911.emergencyHome.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.timife.a911.R
import com.timife.a911.databinding.FragmentCallOptionDialogBinding

class CallOptionDialog : BottomSheetDialogFragment() {
    private lateinit var binding : FragmentCallOptionDialogBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCallOptionDialogBinding.inflate(inflater)
        return binding.root
    }

    companion object {

    }
}