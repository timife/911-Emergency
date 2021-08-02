package com.timife.a911.emergencyPref.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.timife.a911.databinding.FragmentPrivacyBinding


class PrivacyFragment : Fragment() {
  private lateinit var binding : FragmentPrivacyBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPrivacyBinding.inflate(inflater)
        // Inflate the layout for this fragment
        return binding.root
    }

}