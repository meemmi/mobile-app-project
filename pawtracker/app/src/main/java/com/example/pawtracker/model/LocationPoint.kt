package com.example.pawtracker.model

/**
 * Unified LocationPoint class for GPS & Mock Location
 */
data class LocationPoint(
    val lat: Double,       // latitude
    val long: Double,      // longitude
    val time: Long = 0L    // timestamp in milliseconds
)