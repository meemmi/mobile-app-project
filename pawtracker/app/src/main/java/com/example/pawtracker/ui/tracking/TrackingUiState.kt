package com.example.pawtracker.ui.tracking

import com.example.pawtracker.model.LocationPoint

/**
 * UI state for the tracking screen
 */
data class TrackingUiState(
    val currentLocation: LocationPoint? = null,      // current GPS or mock point
    val points: List<LocationPoint> = emptyList(),   // all points collected for polyline
    val tracking: Boolean = false,
    val distance: Float = 0.0f,
    val time: Long = 0L,
    val locationPermission: Boolean = false
)