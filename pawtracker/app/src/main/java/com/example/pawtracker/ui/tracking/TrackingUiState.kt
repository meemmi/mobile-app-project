package com.example.pawtracker.ui.tracking

import com.example.pawtracker.model.LocationPoint

/**
 * Immutable UI state for the Tracking screen.
 *
 * Holds the dog's current GPS location, the full list of tracked points
 * used for drawing the polyline, tracking status, total distance, elapsed time,
 * and whether location permission is granted.
 */

data class TrackingUiState(
    val currentLocation: LocationPoint? = null,
    val points: List<LocationPoint> = emptyList(),
    val tracking: Boolean = false,
    val distance: Double = 0.0,
    val time: Long = 0L,
    val locationPermission: Boolean = false
)