package com.example.pawtracker.data.location

// ViewModel and UI will use of these data
// Cant pass raw Location from APIs into UI
data class LocationPoint(
    val latitude: Double,
    val longitude: Double,
    val time: Long

)