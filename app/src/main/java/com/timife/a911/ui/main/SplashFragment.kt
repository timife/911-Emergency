package com.timife.a911.ui.main

import android.content.Context
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.timife.a911.EmergencyApplication
import com.timife.a911.MainActivity
import com.timife.a911.R
import com.timife.a911.databinding.FragmentSplashBinding
import com.timife.a911.startNewActivity

class SplashFragment : Fragment() {
    private lateinit var binding: FragmentSplashBinding

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as EmergencyApplication).emergencyComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSplashBinding.inflate(inflater)

        Handler().postDelayed({
                val activity = MainActivity::class.java
                requireActivity().startNewActivity(activity)
                requireActivity().overridePendingTransition(R.anim.fade_in,R.anim.fade_out)
        },3000
        )
        return binding.root
    }
}