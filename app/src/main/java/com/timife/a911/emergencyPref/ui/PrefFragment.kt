package com.timife.a911.emergencyPref.ui

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.timife.a911.EmergencyApplication
import com.timife.a911.R
import com.timife.a911.databinding.FragmentPrefBinding

class PrefFragment : Fragment() {
    private lateinit var binding: FragmentPrefBinding



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPrefBinding.inflate(inflater)
        val navController = findNavController()
        binding.privacyPolicy.setOnClickListener {
            this.findNavController().navigate(R.id.action_prefFragment_to_privacyFragment)
        }
        binding.reportIssue.setOnClickListener {
            this.findNavController().navigate(R.id.action_prefFragment_to_reportIssueFragment)
        }
        binding.giveFeedback.setOnClickListener {
            this.findNavController().navigate(R.id.action_prefFragment_to_feedBackFragment)
        }

        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as EmergencyApplication).emergencyComponent.inject(this)
    }
}