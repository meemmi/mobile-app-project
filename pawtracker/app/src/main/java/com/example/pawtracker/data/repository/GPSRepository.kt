package com.example.pawtracker.data.repository

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.os.Looper
import androidx.core.app.ActivityCompat
import com.example.pawtracker.model.LocationPoint
import com.google.android.gms.location.*

class GPSRepository(
    private val context: Context
) {

    private val fusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)

    private lateinit var locationRequest: LocationRequest
    private lateinit var locationCallback: LocationCallback

    private fun hasLocationPermission(): Boolean {
        val fine = ActivityCompat.checkSelfPermission(
            context,
            android.Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        val coarse = ActivityCompat.checkSelfPermission(
            context,
            android.Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        return fine || coarse
    }

    fun setupLocationRequest() {
        locationRequest = LocationRequest.Builder(
            Priority.PRIORITY_HIGH_ACCURACY,
            2000L
        )
            .setMinUpdateIntervalMillis(1000L)
            .setMaxUpdateDelayMillis(3000L)
            .build()
    }

    @SuppressLint("MissingPermission")
    fun startLocationUpdates(onUpdate: (LocationPoint) -> Unit) {

        if (!hasLocationPermission()) return

        setupLocationRequest()

        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                val loc: Location = locationResult.lastLocation ?: return
                onUpdate(
                    LocationPoint(
                        latitude = loc.latitude,
                        longitude = loc.longitude,
                        time = loc.time
                    )
                )
            }
        }

        fusedLocationProviderClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.getMainLooper()
        )
    }

    fun stopLocationUpdates() {
        if (::locationCallback.isInitialized) {
            fusedLocationProviderClient.removeLocationUpdates(locationCallback)
        }
    }

    fun getLastLocation(onResult: (LocationPoint?) -> Unit) {

        if (!hasLocationPermission()) {
            onResult(null)
            return
        }

        try {
            fusedLocationProviderClient.lastLocation
                .addOnSuccessListener { loc: Location? ->
                    if (loc == null) {
                        onResult(null)
                    } else {
                        onResult(
                            LocationPoint(
                                latitude = loc.latitude,
                                longitude = loc.longitude,
                                time = loc.time
                            )
                        )
                    }
                }
                .addOnFailureListener {
                    onResult(null)
                }

        } catch (e: SecurityException) {
            e.printStackTrace()
            onResult(null)
        }
    }
}