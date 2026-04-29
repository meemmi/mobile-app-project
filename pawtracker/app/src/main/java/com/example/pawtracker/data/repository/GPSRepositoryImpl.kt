package com.example.pawtracker.data.repository

import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.os.Looper
import androidx.annotation.RequiresPermission
import androidx.core.app.ActivityCompat
import com.example.pawtracker.model.LocationPoint
import com.google.android.gms.location.*
import android.Manifest


class GPSRepositoryImpl(
    private val context: Context
) : GPSRepository {

    private val fusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)

    private lateinit var locationRequest: LocationRequest
    private lateinit var locationCallback: LocationCallback

    private fun hasLocationPermission(): Boolean {
        val fine = ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        val coarse = ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        return fine || coarse
    }

    fun setupLocationRequest() {
        locationRequest = LocationRequest.Builder(
            Priority.PRIORITY_HIGH_ACCURACY,
            1000L // 1 second
        )
            .setMinUpdateIntervalMillis(500L)
            .setMaxUpdateDelayMillis(2000L)
            .build()
    }
    @RequiresPermission(anyOf = [
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION
    ])
    override fun startLocationUpdates(onUpdate: (LocationPoint) -> Unit) {

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

    override fun stopLocationUpdates() {
        if (::locationCallback.isInitialized) {
            fusedLocationProviderClient.removeLocationUpdates(locationCallback)
        }
    }
    @RequiresPermission(anyOf = [
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION
    ])
    override fun getLastLocation(onResult: (LocationPoint?) -> Unit) {

        if (!hasLocationPermission()) {
            onResult(null)
            return
        }

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
    }
}
