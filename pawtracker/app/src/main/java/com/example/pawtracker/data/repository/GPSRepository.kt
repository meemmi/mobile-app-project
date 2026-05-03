package com.example.pawtracker.data.repository


import com.example.pawtracker.model.LocationPoint

interface GPSRepository {
    fun startLocationUpdates(onUpate: (LocationPoint) -> Unit)
    fun stopLocationUpdates()
    fun getLastLocation(onResult: (LocationPoint?) -> Unit)

}
