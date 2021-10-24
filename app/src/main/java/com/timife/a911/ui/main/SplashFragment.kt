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
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var geocoder: Geocoder
    private lateinit var mFusedLocationPoviderClient: FusedLocationProviderClient


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
        },2000
        )
        return binding.root
    }
//    private fun enableMyLocation(map: GoogleMap) {
//        if (isPermissionGranted()) {
//            if (ActivityCompat.checkSelfPermission(
//                    requireContext(),
//                    Manifest.permission.ACCESS_FINE_LOCATION
//                )
//                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
//                    requireContext(),
//                    Manifest.permission.ACCESS_COARSE_LOCATION
//                )
//                != PackageManager.PERMISSION_GRANTED
//            ) {
//                return
//            }
//            map.isMyLocationEnabled = true
//            updateLocation()
//        } else {
//            requestPermissions(
//                arrayOf(
//                    Manifest.permission.ACCESS_FINE_LOCATION,
//                    Manifest.permission.ACCESS_COARSE_LOCATION
//                ), REQUEST_LOCATION_PERMISSION
//            )
//        }
//    }
//
//    @SuppressLint("VisibleForTests")
//    private fun updateLocation() {
//        val locationRequest = LocationRequest()
//        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
//        locationRequest.interval = 10000
//        locationRequest.fastestInterval = 5000
//        FusedLocationProviderClient(requireActivity()).also {mFusedLocationPoviderClient = it}
//        if (ActivityCompat.checkSelfPermission(
//                requireContext(),
//                Manifest.permission.ACCESS_FINE_LOCATION
//            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
//                requireContext(),
//                Manifest.permission.ACCESS_COARSE_LOCATION
//            ) != PackageManager.PERMISSION_GRANTED
//        ) {
//            return
//        }
//        mFusedLocationPoviderClient.requestLocationUpdates(
//            locationRequest,
//            mLocationCallback,
//            Looper.myLooper()
//        )
//    }
//
//    private var mLocationCallback = object : LocationCallback() {
//        override fun onLocationResult(locationResult: LocationResult?) {
//            val location: Location = locationResult!!.lastLocation
//            val homeLatLng = LatLng(location.latitude, location.longitude)
//            val zoomLevel = 15f
//            try {
//                val addressList: ArrayList<Address> = geocoder.getFromLocation(
//                    location.latitude,
//                    location.longitude,
//                    1
//                ) as ArrayList<Address>
//
//                updateAddressUI(location)
//            } catch (e: IOException) {
//            }
//        }
//    }
//
//    @SuppressLint("SetTextI18n")
//    private fun updateAddressUI(location: Location) {
//        val addressList: ArrayList<Address>
//        try {
//            addressList = geocoder.getFromLocation(
//                location.latitude,
//                location.longitude,
//                1
//            ) as ArrayList<Address>
//
//            // Save Country and state in shared preferences
//            val editor = sharedPreferences.edit()
//            editor.apply {
//                putString("country", addressList[0].countryName)
//                putString("state", addressList[0].adminArea)
//                apply()
//            }
//        } catch (e: Exception) {
//        }
//    }
}