package com.timife.a911.emergencyHome.ui

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SwitchCompat
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
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
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.timife.a911.EmergencyApplication
import com.timife.a911.databinding.FragmentHomeBinding
import java.io.IOException
import java.util.*
import javax.inject.Inject

const val REQUEST_LOCATION_PERMISSION = 1

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class HomeFragment : Fragment(), OnMapReadyCallback {

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var geocoder: Geocoder

    companion object {
        private const val MAPVIEW_BUNDLE_KEY = "MapViewBundleKey"
    }

    private lateinit var mapView: MapView
    private lateinit var map: GoogleMap
    private lateinit var binding: FragmentHomeBinding
    private lateinit var adapter: ViewPagerAdapter

    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout

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
        sharedPreferences =
            requireActivity().getSharedPreferences("countryPref", Context.MODE_PRIVATE)

        binding = FragmentHomeBinding.inflate(inflater)
        geocoder = Geocoder(requireContext(), Locale.getDefault())

        initGoogleMap(savedInstanceState)
        viewPager = binding.viewPager
        tabLayout = binding.tabLayout
        setupViewPager(tabLayout, viewPager)

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
    //View pager different fragments

    private fun setupViewPager(tabLayout: TabLayout, viewPager: ViewPager2) {
        adapter =
            ViewPagerAdapter(childFragmentManager, lifecycle)
        addFragmentPagerData()
        viewPager.adapter = adapter
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            when (position) {
                0 -> {
                    tab.text = "EMERGENCY \n SERVICES"
                }
                1 -> tab.text = "NON-EMERGENCY \n SERVICES"
            }
        }.attach()
    }

    private var emergencyFragmentInstance = HomeFragmentCategory.newInstance(EMERGENCY_SERVICES)
    private var nonEmergencyFragmentInstance = HomeFragmentCategory.newInstance(
        NON_EMERGENCY_SERVICES
    )

    private fun addFragmentPagerData() {
        adapter.addFragment(emergencyFragmentInstance)
        adapter.addFragment(nonEmergencyFragmentInstance)
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
        locationRequest.interval = 10000
        locationRequest.fastestInterval = 5000
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
            try {
                val addressList: ArrayList<Address> = geocoder.getFromLocation(
                    location.latitude,
                    location.longitude,
                    1
                ) as ArrayList<Address>
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(homeLatLng, zoomLevel))
                val state = sharedPreferences.getString("state", "loading...")
                val country = sharedPreferences.getString("country", "loading...")
                val address = "$state,$country"
                val snippet = "You are at " + addressList[0].getAddressLine(0)
                val infoWindow = map.addMarker(
                    MarkerOptions().position(homeLatLng).title(address).snippet(snippet)
                )
                updateAddressUI(location)
                infoWindow!!.showInfoWindow()
            } catch (e: IOException) {
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun updateAddressUI(location: Location) {
        val addressList: ArrayList<Address>
        try {
            addressList = geocoder.getFromLocation(
                location.latitude,
                location.longitude,
                1
            ) as ArrayList<Address>
            binding.locationAddress.text = "You are at " + addressList[0].getAddressLine(0)

            // Save Country and state in shared preferences
            val editor = sharedPreferences.edit()
            editor.apply {
                putString("country", addressList[0].countryName)
                putString("state", addressList[0].adminArea)
                apply()
            }
        } catch (e: Exception) {
            binding.locationLayout.visibility = View.GONE
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == REQUEST_LOCATION_PERMISSION) {
            if (grantResults.isNotEmpty() && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                enableMyLocation(map)
            }
        }
    }
}