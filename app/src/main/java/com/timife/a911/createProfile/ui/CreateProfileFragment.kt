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
}