package com.example.pawtracker.data.repository

import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.os.Looper
import androidx.core.app.ActivityCompat
import com.example.pawtracker.data.location.LocationPoint
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
            60_000L // 60 seconds
        )
            .setMinUpdateIntervalMillis(30_000L)
            .setMaxUpdateDelayMillis(120_000L)
            .build()
    }

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

        fusedLocationProviderClient.lastLocation
            .addOnSuccessListener { loc ->
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
    }
}
