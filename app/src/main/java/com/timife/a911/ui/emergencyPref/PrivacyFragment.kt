package com.timife.a911.ui.emergencyPref

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.ravikoradiya.library.CenterTitle
import com.timife.a911.databinding.FragmentPrivacyBinding


class PrivacyFragment : Fragment() {
  private lateinit var binding : FragmentPrivacyBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPrivacyBinding.inflate(inflater)
        CenterTitle.centerTitle(binding.privacyToolbar,true)
        val navController =findNavController()
        binding.privacyToolbar.setupWithNavController(navController)
        // Inflate the layout for this fragment
        return binding.root
    }

}