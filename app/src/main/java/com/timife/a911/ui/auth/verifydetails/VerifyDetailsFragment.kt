package com.timife.a911.ui.auth.verifydetails

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.timife.a911.EmergencyApplication
import com.timife.a911.R
import com.timife.a911.databinding.FragmentVerifyDetailsBinding
import com.timife.a911.ui.BaseFragment
import com.timife.a911.utils.states.DataState

class VerifyDetailsFragment : BaseFragment() {
    private lateinit var binding : FragmentVerifyDetailsBinding
    private val verifyDetailsViewModel by viewModels<VerifyDetailsViewModel> { viewModelFactoryProvider }
    private val args: VerifyDetailsFragmentArgs by navArgs()


    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as EmergencyApplication).emergencyComponent.inject(this)
    }




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentVerifyDetailsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.backToLogin.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.verify.setOnClickListener {
            resendVerificationLink(args.email, args.password)
        }
    }


    private fun resendVerificationLink(email: String, password: String) {
        verifyDetailsViewModel.resendVerificationLink(email, password).observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is DataState.Success -> {
                    binding.progressVerify.visibility = View.GONE
                    showSnackBar(result.data)
                    findNavController().navigateUp()
                }
                is DataState.Loading -> {
                    binding.progressVerify.visibility = View.VISIBLE
                }
                is DataState.Error -> {
                    binding.progressVerify.visibility = View.GONE
                }
            }
        })
    }

    private fun showSnackBar(message: String) {
        Snackbar.make(binding.verifyCoordinator, message, Snackbar.LENGTH_SHORT).show()
    }
}