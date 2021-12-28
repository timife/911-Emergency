package com.timife.a911.ui.splash

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.timife.a911.EmergencyApplication
import com.timife.a911.MainActivity
import com.timife.a911.R
import com.timife.a911.databinding.FragmentSplashBinding
import com.timife.a911.extensions.navigateSafe
import com.timife.a911.utils.Constants
import com.timife.a911.utils.startNewActivity
import com.timife.a911.utils.states.AuthResult
import javax.inject.Inject

class SplashFragment : Fragment() {
    private lateinit var binding: FragmentSplashBinding

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var preferences: SharedPreferences

    private val viewModel by viewModels<SplashViewModel> { viewModelFactory }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as EmergencyApplication).emergencyComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =FragmentSplashBinding.inflate(inflater)


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Handler().postDelayed({
            context?.let {
                setupUI()
            }
        }, 2500)
    }

    private fun observeAuthState() {
        viewModel.currentUserStatus.observe(viewLifecycleOwner, { result ->
            when (result) {
                is AuthResult.Failed -> {
                    navigateSafe(SplashFragmentDirections.actionSplashScreenFragmentToAuthNavigation())
                }

                is AuthResult.Success -> {
                    navigateSafe(SplashFragmentDirections.actionSplashScreenFragmentToHomeFragment())
                }
            }
        })
    }

    private fun setupUI() {
        if (preferences.getBoolean(Constants.IS_FIRST_TIME_USER_KEY, true)) {
            navigateSafe(SplashFragmentDirections.actionSplashScreenFragmentToOnBoardingFragment())
        } else {
            observeAuthState()
        }
    }
}