package com.timife.a911.emergencyPref.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.ravikoradiya.library.CenterTitle
import com.timife.a911.databinding.FragmentReportIssueBinding


class ReportIssueFragment : Fragment() {
    private lateinit var binding: FragmentReportIssueBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentReportIssueBinding.inflate(inflater)
        CenterTitle.centerTitle(binding.reportToolbar,true)
        val navController =findNavController()
        binding.reportToolbar.setupWithNavController(navController)
        // Inflate the layout for this fragment
        return binding.root
    }

}