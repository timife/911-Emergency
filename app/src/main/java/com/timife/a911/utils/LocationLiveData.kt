package com.timife.a911.utils

import android.annotation.SuppressLint
import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.location.Location
import androidx.lifecycle.LiveData
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.timife.a911.data.model.LocationModel
import java.io.IOException
import java.util.*

/**
 * Created by Ademola Timothy on 18-12-2021
 */

class LocationLiveData(context: Context) : LiveData<LocationModel>() {
    private var geocoder = Geocoder(context, Locale.getDefault())
//    private lateinit var address: Address

    private var fusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)

    @SuppressLint("MissingPermission")
    override fun onActive() {
        super.onActive()
        fusedLocationProviderClient.lastLocation.addOnSuccessListener {
            location : Location? ->
            location?.also {
                setLocationData(it)
            }
        }
        startLocationUpdates()
    }

    @SuppressLint("MissingPermission")
    private fun startLocationUpdates() {
        fusedLocationProviderClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            null
        )
    }

    override fun onInactive() {
        super.onInactive()
        fusedLocationProviderClient.removeLocationUpdates(locationCallback)
    }

    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            locationResult ?: return
            val location: Location = locationResult.lastLocation
            try{
                setLocationData(location)
            }catch (exception:IOException){

            }
        }
    }

    private fun setLocationData(location: Location?) {
//        address = geocoder.getFromLocation(
//            location!!.longitude,
//            location.latitude,
//            1
//        ) as Address

        value =LocationModel(
            location!!.longitude,
            location.latitude
        )
    }

    companion object {
        val locationRequest : LocationRequest = LocationRequest.create().apply {
            interval = 10000
            fastestInterval = 5000
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }
    }

}