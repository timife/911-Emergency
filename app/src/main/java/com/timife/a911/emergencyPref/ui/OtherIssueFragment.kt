package com.timife.a911.emergencyPref.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.timife.a911.R
import com.timife.a911.databinding.FragmentOtherIssueBinding


class OtherIssueFragment : Fragment() {
    private lateinit var binding: FragmentOtherIssueBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentOtherIssueBinding.inflate(inflater)
        val navController =findNavController()
        binding.reportIssueToolbar.setupWithNavController(navController)
        return binding.root
    }
}