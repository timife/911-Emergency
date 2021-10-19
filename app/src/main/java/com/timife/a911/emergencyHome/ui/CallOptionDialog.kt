package com.timife.a911.emergencyHome.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.timife.a911.EmergencyApplication
import com.timife.a911.R
import com.timife.a911.databinding.FragmentCallOptionDialogBinding
import javax.inject.Inject

class CallOptionDialog : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentCallOptionDialogBinding

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

        binding.cancelOption.setOnClickListener {
            dismiss()
        }
        binding.saveOption.setOnClickListener {
            val number = argument.name
            Toast.makeText(requireContext(), "$number saved successfully", Toast.LENGTH_SHORT)
                .show()
        }
        binding.reportOption.setOnClickListener {
            val report = argument.phone
            Toast.makeText(requireContext(), "$report successfully reported", Toast.LENGTH_SHORT)
                .show()

            val singleItems = arrayOf("Displayed number is disconnected", "Displayed number is incorrect")
            var checkedItem = 0

            var selectedItem = singleItems[checkedItem]


            MaterialAlertDialogBuilder(requireContext())
                .setTitle("Report this number because...")

                // Single-choice items (initialized with checked item)
                .setSingleChoiceItems(singleItems, checkedItem) { dialog, which ->
                    // Respond to item chosen
                    checkedItem = which
                    selectedItem = singleItems[which]
                }
                .setPositiveButton(getString(R.string.send_report)){ dialog, which ->
                    Toast.makeText(requireContext(), "$selectedItem selected", Toast.LENGTH_LONG)
                        .show()
                }
                .show()
        }
        return binding.root
    }

    companion object {

    }

    override fun getTheme(): Int {
        return R.style.AppBottomSheetDialogFragment
    }
}