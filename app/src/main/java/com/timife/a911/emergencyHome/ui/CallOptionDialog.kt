package com.timife.a911.emergencyHome.ui

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.timife.a911.EmergencyApplication
import com.timife.a911.R
import com.timife.a911.databinding.FragmentCallOptionDialogBinding
import javax.inject.Inject

class CallOptionDialog : BottomSheetDialogFragment() {

    private lateinit var binding : FragmentCallOptionDialogBinding

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by viewModels<CallOptionViewModel> { viewModelFactory }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as EmergencyApplication).emergencyComponent.inject(this)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentCallOptionDialogBinding.inflate(inflater)
        val argument = CallOptionDialogArgs.fromBundle(requireArguments()).selectedNumber

        binding.cancelOption.setOnClickListener{
            dismiss()
        }
        binding.saveOption.setOnClickListener {
            val number = argument.name
            Toast.makeText(requireContext(),"$number saved successfully",Toast.LENGTH_SHORT).show()
        }
        binding.reportOption.setOnClickListener {
            val report = argument.phone
            Toast.makeText(requireContext(),"$report successfully reported",Toast.LENGTH_SHORT).show()
        }


//        viewModel.selectedNumber.observe(viewLifecycleOwner,{
//
//        })


        return binding.root
    }

    companion object {

    }

    override fun getTheme(): Int {
        return R.style.AppBottomSheetDialogFragment
    }
}