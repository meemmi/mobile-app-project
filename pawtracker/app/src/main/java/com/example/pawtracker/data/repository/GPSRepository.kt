package com.example.pawtracker.data.repository
import com.example.pawtracker.model.LocationPoint
/**
 * Repository interface for accessing GPS location data.
 * Starts and stops continuous location updates and retrieves the last known location.
 */
interface GPSRepository {
    fun startLocationUpdates(onUpdate: (LocationPoint) -> Unit)
    fun stopLocationUpdates()
    fun getLastLocation(onResult: (LocationPoint?) -> Unit)

}
