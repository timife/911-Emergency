package com.timife.a911.ui.emergencyHome

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
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
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.timife.a911.EmergencyApplication
import com.timife.a911.R
import com.timife.a911.databinding.FragmentHomeBinding
import com.timife.a911.ui.BaseFragment
import com.timife.a911.utils.Constants.GPS_REQUEST_CHECK_SETTINGS
import com.timife.a911.utils.GpsUtil
import com.timife.a911.utils.observeOnce
import java.util.*
import javax.inject.Inject

const val REQUEST_LOCATION_PERMISSION = 1

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class HomeFragment : BaseFragment(), OnMapReadyCallback {
    private var isGPSEnabled = false

    companion object {
        private const val MAPVIEW_BUNDLE_KEY = "MapViewBundleKey"
        private val REQUIRED_PERMISSIONS = arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
        const val LOCATION_REQUEST_CODE = 123
    }

    private lateinit var mapView: MapView
    private lateinit var map: GoogleMap
    private lateinit var binding: FragmentHomeBinding
    private lateinit var adapter: ViewPagerAdapter
    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout


    @Inject
    lateinit var geocoder: Geocoder

    private val viewModel by viewModels<HomeViewModel> { viewModelFactoryProvider }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as EmergencyApplication).emergencyComponent.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        GpsUtil(requireContext()).turnGPSOn(object : GpsUtil.OnGpsListener {
            override fun gpsStatus(isGPSEnabled: Boolean) {
                this@HomeFragment.isGPSEnabled = isGPSEnabled
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentHomeBinding.inflate(inflater)

        initGoogleMap(savedInstanceState)
        viewPager = binding.viewPager
        tabLayout = binding.tabLayout
        setupViewPager(tabLayout, viewPager)
        switchVisibility()
        return binding.root
    }

    private fun switchVisibility() {
        //MapView Switch functionality
        val switch: SwitchCompat = binding.mapSwitch
        switch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                binding.map.visibility = View.VISIBLE
                binding.locationLayout.visibility = View.GONE
                binding.profileImageLogo.visibility = View.GONE
                binding.mapSwitch.setTextColor(Color.BLACK)
            } else {
                binding.map.visibility = View.GONE
                binding.locationLayout.visibility = View.VISIBLE
                binding.profileImageLogo.visibility = View.VISIBLE
                binding.mapSwitch.setTextColor(Color.WHITE)
            }
        }
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
        super.onStart()
        invokeLocationAction()
        mapView.onStart()
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

    private fun invokeLocationAction() {
        when {
            allPermissionsGranted() -> {
                viewModel.fetchLocationLiveData().observeOnce(
                    viewLifecycleOwner
                ) { location ->
                    if (location != null) {
//                            mapDetails()
//                                viewModel.getWeather(location)
//                                setupWorkManager()

                    }
                }
            }

            shouldShowRequestPermissionRationale() -> {
                AlertDialog.Builder(requireContext())
                    .setTitle(getString(R.string.location_permission))
                    .setMessage(getString(R.string.access_location_message))
                    .setNegativeButton(
                        getString(R.string.no)
                    ) { _, _ -> requireActivity().finish() }
                    .setPositiveButton(
                        getString(R.string.ask_me)
                    ) { _, _ ->
                        requestPermissions(REQUIRED_PERMISSIONS, LOCATION_REQUEST_CODE)
                    }
                    .show()
            }

            !isGPSEnabled -> {
                Snackbar.make(
                    requireView(),
                    getString(R.string.gps_required_message),
                    Snackbar.LENGTH_SHORT
                ).show()
            }

            else -> {
                requestPermissions(REQUIRED_PERMISSIONS, LOCATION_REQUEST_CODE)
            }
        }
    }

    private fun enableMyLocation(map: GoogleMap) {
        if (allPermissionsGranted()) {
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
            mapDetails()
        } else {
            requestPermissions(
                REQUIRED_PERMISSIONS, LOCATION_REQUEST_CODE
            )
        }
    }

    @SuppressLint("SetTextI18n")
    fun mapDetails() {
        viewModel.locationLivedata.observeOnce(viewLifecycleOwner, {
            map.clear()
            val homeLatLng = LatLng(it.latitude, it.longitude)
            val zoomLevel = 15f
            try {
                val addressList: ArrayList<Address> = geocoder.getFromLocation(
                    it.latitude,
                    it.longitude,
                    1
                ) as ArrayList<Address>
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(homeLatLng, zoomLevel))
                val mapTag = "${addressList[0].adminArea},${addressList[0].countryName}"
                val snippet = "You are at " + addressList[0].getAddressLine(0)
                val infoWindow = map.addMarker(
                    MarkerOptions().position(homeLatLng).title(mapTag).snippet(snippet)
                )
                binding.locationAddress.text = "You are at " + addressList[0].getAddressLine(0)
                infoWindow!!.showInfoWindow()
            } catch (exception: java.lang.Exception) {
                binding.locationLayout.visibility = View.GONE
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            GPS_REQUEST_CHECK_SETTINGS -> {
                when (resultCode) {
                    Activity.RESULT_OK -> {
                        isGPSEnabled = true
                        invokeLocationAction()
                    }

                    Activity.RESULT_CANCELED -> {
                        Snackbar.make(
                            binding.root,
                            getString(R.string.enable_gps),
                            Snackbar.LENGTH_LONG
                        ).show()
                    }
                }
            }
        }
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(requireContext(), it) == PackageManager.PERMISSION_GRANTED
    }

    private fun shouldShowRequestPermissionRationale() = REQUIRED_PERMISSIONS.all {
        shouldShowRequestPermissionRationale(it)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == LOCATION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                invokeLocationAction()
            }
        }
    }
}