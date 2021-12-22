package com.timife.a911.ui.EmergencyPref

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
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


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.privacyPolicyPref.setOnClickListener {
            this.findNavController().navigate(R.id.action_prefFragment_to_privacyFragment)
        }
        binding.reportIssuePref.setOnClickListener {
            this.findNavController().navigate(R.id.action_prefFragment_to_reportIssueFragment)
        }
        binding.giveFeedbackPref.setOnClickListener {
            this.findNavController().navigate(R.id.action_prefFragment_to_feedBackFragment)
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as EmergencyApplication).emergencyComponent.inject(this)
    }
}