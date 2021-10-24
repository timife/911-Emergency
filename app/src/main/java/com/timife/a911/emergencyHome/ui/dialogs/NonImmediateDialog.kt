package com.timife.a911.emergencyHome.ui.dialogs

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.timife.a911.R
import com.timife.a911.databinding.FragmentNonImmediateDialogBinding


class NonImmediateDialog : DialogFragment() {
    private lateinit var binding: FragmentNonImmediateDialogBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNonImmediateDialogBinding.inflate(inflater)
        binding.cancel.setOnClickListener {
            dismiss()
        }
        return binding.root
    }

}