package com.timife.a911.emergencyPref.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.ravikoradiya.library.CenterTitle
import com.timife.a911.databinding.FragmentFeedBackBinding

class FeedBackFragment : Fragment() {
private lateinit var binding: FragmentFeedBackBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        binding = FragmentFeedBackBinding.inflate(inflater)
        CenterTitle.centerTitle(binding.feedbackToolbar,true)
        // Inflate the layout for this fragment
        val navController = findNavController()
        binding.feedbackToolbar.setupWithNavController(navController)
        return binding.root
    }
}