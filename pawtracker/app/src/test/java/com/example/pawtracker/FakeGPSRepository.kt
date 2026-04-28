package com.example.pawtracker

import com.example.pawtracker.data.repository.GPSRepository
import com.example.pawtracker.model.LocationPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FakeGPSRepository : GPSRepository {
    var started = false
    var stopped = false
    var lastLocation: LocationPoint? = null
    private var callback: ((LocationPoint) -> Unit)? = null
    override fun startLocationUpdates(onUpdate: (LocationPoint) -> Unit) {
        started = true
        callback = onUpdate
        println("CALLBACK SET = $callback")}
    override fun stopLocationUpdates() {
        stopped = true }
    override fun getLastLocation(onResult: (LocationPoint?) -> Unit) {
        onResult(lastLocation)
    }
    fun emit(point: LocationPoint) {
        callback?.invoke(point)
    }


}