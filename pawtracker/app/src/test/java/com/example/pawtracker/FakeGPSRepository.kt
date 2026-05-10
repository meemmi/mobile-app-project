package com.example.pawtracker

import com.example.pawtracker.data.repository.GPSRepository
import com.example.pawtracker.model.LocationPoint
/**
 * Fake GPSRepository for tracking tests.
 * Captures start/stop calls, stores the last location,
 * and allows tests to emit mock GPS updates through the callback.
 */
class FakeGPSRepository : GPSRepository {

    private var callback: ((LocationPoint) -> Unit)? = null

    var started = false
    var lastLocation: LocationPoint? = null

    override fun startLocationUpdates(onUpdate: (LocationPoint) -> Unit) {
        started = true
        callback = onUpdate
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