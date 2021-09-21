package com.timife.a911.emergencyHome.ui

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.opengl.Visibility
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SwitchCompat
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.timife.a911.EmergencyApplication
import com.timife.a911.databinding.FragmentHomeBinding
import java.util.*
import javax.inject.Inject

const val REQUEST_LOCATION_PERMISSION = 1

class HomeFragment : Fragment(), OnMapReadyCallback {

    companion object {
        private const val MAPVIEW_BUNDLE_KEY = "MapViewBundleKey"
    }

    private lateinit var mapView: MapView
    private lateinit var map: GoogleMap

    private lateinit var binding: FragmentHomeBinding

    private lateinit var mFusedLocationPoviderClient: FusedLocationProviderClient

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by viewModels<HomeViewModel> { viewModelFactory }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as EmergencyApplication).emergencyComponent.inject(this)
    }




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater)
        isLocationServicesEnabled()
        initGoogleMap(savedInstanceState)

        //MapView Switch functionality
        val switch: SwitchCompat = binding.mapSwitch
        switch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                binding.map.visibility = View.VISIBLE
                binding.locationLayout.visibility = View.GONE
                binding.profileImage.visibility = View.GONE
                binding.mapSwitch.setTextColor(Color.BLACK)
            } else {
                binding.map.visibility = View.GONE
                binding.locationLayout.visibility = View.VISIBLE
                binding.profileImage.visibility = View.VISIBLE
                binding.mapSwitch.setTextColor(Color.WHITE)
            }
        }

        return binding.root
    }

    //Check if location and GPS is enabled or otherwise.
    private fun isLocationServicesEnabled() {
        val lm: LocationManager =
            requireActivity().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        var gpsEnabled = false

        try {
            gpsEnabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER)
        } catch (ex: java.lang.Exception) {

        }

        if (!gpsEnabled) {
            // notify user
            AlertDialog.Builder(context)
                .setMessage("You need to turn on Location Services")
                .setPositiveButton(
                    "Enable Location"
                ) { _, _ -> requireActivity().startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)) }
                .setNegativeButton("Close", null)
                .show()
        }
    }

    private fun initGoogleMap(savedInstanceState: Bundle?) {
        val mapViewBundle = savedInstanceState?.getBundle(MAPVIEW_BUNDLE_KEY)
        mapView = binding.map
        mapView.onCreate(mapViewBundle)
        mapView.getMapAsync(this)
    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val mapViewBundle = outState.getBundle(MAPVIEW_BUNDLE_KEY) ?: Bundle().apply {
            putBundle(MAPVIEW_BUNDLE_KEY, this)
        }
        mapView.onSaveInstanceState(mapViewBundle)
    }

    override fun onResume() {
        mapView.onResume()
        super.onResume()
    }

    override fun onStart() {
        mapView.onStart()
        super.onStart()
    }

    override fun onStop() {
        mapView.onStop()
        super.onStop()
    }

    override fun onMapReady(map: GoogleMap) {
        this.map = map
        enableMyLocation(map)

    }

    override fun onPause() {
        mapView.onPause()
        super.onPause()
    }

    override fun onDestroy() {
        mapView.onDestroy()
        super.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }

    //recheck
    private fun isPermissionGranted(): Boolean {
        return ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun enableMyLocation(map: GoogleMap) {
        if (isPermissionGranted()) {
            if (ActivityCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
                != PackageManager.PERMISSION_GRANTED
            ) {
                return
            }
            map.isMyLocationEnabled = true
            updateLocation()
            binding.locationAddress.text = updateLocation().toString()
        } else {
            requestPermissions(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ), REQUEST_LOCATION_PERMISSION
            )
        }
    }

    @SuppressLint("VisibleForTests")
    private fun updateLocation() {
        val locationRequest = LocationRequest()
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest.interval = 5000
        locationRequest.fastestInterval = 2500
        FusedLocationProviderClient(requireActivity()).also { mFusedLocationPoviderClient = it }
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        mFusedLocationPoviderClient.requestLocationUpdates(
            locationRequest,
            mLocationCallback,
            Looper.myLooper()
        )

    }

    private var mLocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult?) {
            val location: Location = locationResult!!.lastLocation
            map.clear()
            val homeLatLng = LatLng(location.latitude, location.longitude)
            val zoomLevel = 15f
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(homeLatLng, zoomLevel))
            map.addMarker(MarkerOptions().position(homeLatLng))

            updateAddressUI(location)
        }
    }

    private fun updateAddressUI(location: Location) {
        val geocoder: Geocoder
        val addressList: ArrayList<Address>
        try {
            geocoder = Geocoder(requireContext(), Locale.getDefault())
            addressList = geocoder.getFromLocation(
                location.latitude,
                location.longitude,
                1
            ) as ArrayList<Address>
            binding.locationAddress.text = "You are at " + addressList[0].getAddressLine(0)
            setUpEmergencyNumbers(addressList[0].adminArea, addressList[0].countryName)
        } catch (e: Exception) {
            binding.locationAddress.text = "Error loading address"
        }
    }

    private fun setUpEmergencyNumbers(adminArea: String?, countryName: String?) {
//        Toast.makeText(requireContext(), "This is a setup Emergency", Toast.LENGTH_LONG).show()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == REQUEST_LOCATION_PERMISSION) {
            if (grantResults.isNotEmpty() && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                enableMyLocation(map)
                //get address
            }
        }
    }


}