package com.timife.a911

import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.timife.a911.databinding.ActivityMainBinding
import com.timife.a911.extensions.hide
import com.timife.a911.extensions.show

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var bottomNavigation:BottomNavigationView
    private lateinit var navController: NavController



    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        (application as EmergencyApplication).emergencyComponent.inject(this)
        setUpNavigation()
    }

    private fun setUpNavigation() {
        bottomNavigation = binding.bottomNavView
        navController = findNavController(R.id.nav_host_fragment)

        appBarConfiguration = AppBarConfiguration(setOf(
            R.id.homeFragment,
            R.id.searchFragment,
            R.id.saveFragment,
            R.id.prefFragment
        ))

        NavigationUI.setupWithNavController(bottomNavigation,navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when(destination.id){
                R.id.splashScreenFragment,
                R.id.onBoardingFragment,
                R.id.signUpFragment,
                R.id.loginFragment,
                R.id.verifyPasswordFragment,
                R.id.resetPasswordFragment
                -> {
                    bottomNavigation.hide()
                }
                else -> {
                    Handler().postDelayed({
                        applicationContext.let {
                            bottomNavigation.show()
                        }
                    }, 1000)
                }
            }

        }
    }

    override fun onBackPressed() {
        if (navController.currentDestination!!.id == R.id.homeFragment) {
            navController.popBackStack(R.id.emergency_graph, true)
        }
        super.onBackPressed()
    }
}