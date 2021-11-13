package com.timife.a911.ui.main

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.timife.a911.EmergencyApplication
import com.timife.a911.MainActivity
import com.timife.a911.R
import com.timife.a911.databinding.FragmentSplashBinding
import com.timife.a911.emergencyHome.ui.REQUEST_LOCATION_PERMISSION
import com.timife.a911.startNewActivity
import java.io.IOException
import java.util.ArrayList

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
        },7500
        )
        return binding.root
    }
}