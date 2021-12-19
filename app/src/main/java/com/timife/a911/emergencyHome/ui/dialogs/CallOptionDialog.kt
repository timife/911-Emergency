package com.timife.a911.emergencyHome.ui.dialogs

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
            MaterialAlertDialogBuilder(
                requireContext(),
                R.style.ThemeOverlay_MaterialComponents_Dialog
            )
                .setIcon(R.drawable.ic_account_circle)
                .setTitle("Create Profile")
                .setMessage("Create a Profile so you can save a Number.")
                .setPositiveButton("Sign-in") { _, _ ->
                    Toast.makeText(requireContext(), "sign-in", Toast.LENGTH_SHORT).show()
                }.setNegativeButton("Create-Profile") { _, _ ->
                    Toast.makeText(requireContext(), "Create Profile", Toast.LENGTH_SHORT).show()
                }.show()
        }
        binding.reportOption.setOnClickListener {
            val report = argument.phone
            val singleItems =
                arrayOf("Displayed number is disconnected", "Displayed number is incorrect")
            var checkedItem = 0
            var selectedItem = singleItems[checkedItem]

            MaterialAlertDialogBuilder(requireContext())
                .setTitle("Report this number because...")
                // Single-choice items (initialized with checked item)
                .setSingleChoiceItems(singleItems, checkedItem) { _, which ->
                    // Respond to item chosen
                    checkedItem = which
                    selectedItem = singleItems[which]
                }
                .setPositiveButton(getString(R.string.send_report)) { dialog, which ->
                    Toast.makeText(requireContext(), "$selectedItem selected", Toast.LENGTH_LONG)
                        .show()
                }
                .show()
        }
        return binding.root
    }

    override fun getTheme(): Int {
        return R.style.AppBottomSheetDialogFragment
    }
}