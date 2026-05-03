package com.example.pawtracker.model

/**
 * Unified LocationPoint class for GPS & Mock Location
 */
data class LocationPoint(
    val latitude: Double,
    val longitude: Double,
    val time: Long = 0L
)