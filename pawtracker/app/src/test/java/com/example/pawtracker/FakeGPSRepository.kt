package com.example.pawtracker

import com.example.pawtracker.data.repository.GPSRepository
import com.example.pawtracker.model.LocationPoint

class FakeGPSRepository : GPSRepository {

    private var callback: ((LocationPoint) -> Unit)? = null

    var started = false
    var lastLocation: LocationPoint? = null

    override fun startLocationUpdates(onLocation: (LocationPoint) -> Unit) {
        started = true
        callback = onLocation
    }

    override fun stopLocationUpdates() {
        started = false
        callback = null
    }

    override fun getLastLocation(onResult: (LocationPoint?) -> Unit) {
        onResult(lastLocation)
    }

    fun emit(point: LocationPoint) {
        callback?.invoke(point)
    }
}