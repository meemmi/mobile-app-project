package com.example.pawtracker.ui.tracking

import com.example.pawtracker.model.LocationPoint

data class TrackingUiState(
    val currentLocation: LocationPoint? = null,
    val points: List<LocationPoint> = emptyList(),
    val tracking: Boolean = false
)
