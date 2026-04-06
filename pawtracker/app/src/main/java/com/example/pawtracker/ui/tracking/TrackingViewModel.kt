package com.example.pawtracker.ui.tracking

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pawtracker.data.local.WalkEntity
import com.example.pawtracker.model.LocationPoint
import com.example.pawtracker.data.repository.GPSRepository
import com.example.pawtracker.data.repository.WalkRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import android.location.Location



/**
 * Unified TrackingViewModel
 * - Uses GPSRepository for real GPS
 * - Uses TrackingUiState for map UI
 * - Optional mock location for testing
 */
class TrackingViewModel(
    private val gpsRepository: GPSRepository,
    private val walkRepository: WalkRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(TrackingUiState())
    val uiState: StateFlow<TrackingUiState> = _uiState


    private var useMockLocation = false // set true for testing mock data
    private var startTime: Long = 0L
    private var lastPoint: LocationPoint? = null




    /**
     * Optional mock location flow (for testing)
     */
    private fun mockLocationFlow(): Flow<LocationPoint> = flow {
        val mockRoute = listOf(
            LocationPoint(60.1699, 24.9384),
            LocationPoint(60.1705, 24.9392),
            LocationPoint(60.1712, 24.9401),
            LocationPoint(60.1720, 24.9410),
            LocationPoint(60.1730, 24.9420),
            LocationPoint(60.1736, 24.9428),
            LocationPoint(60.1740, 24.9432),
            LocationPoint(60.1744, 24.9436),
            LocationPoint(60.1748, 24.9440),
            LocationPoint(60.1752, 24.9445),
        )
        for (point in mockRoute) {
            delay(1000) // emit every second
            emit(point)
        }
    }

    /**
     * Start tracking either real GPS or mock location
     */
    fun startTracking() {
        _uiState.update { it.copy(tracking = true) }
        startTime = System.currentTimeMillis()
        lastPoint = null

        if (useMockLocation) {
            viewModelScope.launch {
                mockLocationFlow().collect { point ->
                    _uiState.update { state ->

                        // Calculate distance
                        val addedDistance = if (lastPoint != null) {
                            calculateDistance(lastPoint!!, point)
                        } else 0.0

                        lastPoint = point

                        // Calculate time
                        val elapsedMinutes =
                            (System.currentTimeMillis() - startTime) / 60000
                        state.copy(
                            currentLocation = point,
                            points = state.points + point
                        )
                    }
                }
            }
        } else {
            gpsRepository.startLocationUpdates { point ->
                viewModelScope.launch {
                    _uiState.update { state ->

                        // Calculate distance
                        val addedDistance = if (lastPoint != null) {
                            calculateDistance(lastPoint!!, point)
                        } else 0.0

                        lastPoint = point

                        // Calculate time
                        val elapsedMinutes =
                            (System.currentTimeMillis() - startTime) / 60000


                        state.copy(
                            currentLocation = point,
                            points = state.points + point
                        )
                    }
                }
            }
        }
    }

    /**
     * Stop tracking
     */
    fun stopTracking() {
        _uiState.update { it.copy(tracking = false) }
        if (!useMockLocation) {
            gpsRepository.stopLocationUpdates()
        }

        // Add route data to walk database
        viewModelScope.launch {
            val walk = WalkEntity(
                startTime = System.currentTimeMillis() - 60_000,
                endTime = System.currentTimeMillis(),
                distance = 120f,
                duration = 60_000,
                path = _uiState.value.points
            )
            walkRepository.insertWalk(walk)
        }
    }

    /**
     * Load last known location (only real GPS)
     */
    fun loadLastLocation() {
        if (!useMockLocation) {
            gpsRepository.getLastLocation { point ->
                viewModelScope.launch {
                    _uiState.update { state ->
                        state.copy(currentLocation = point)
                    }
                }
            }
        }
    }

    /**
     * Enable or disable mock location for testing
     */
    fun setUseMockLocation(enable: Boolean) {
        useMockLocation = enable
    }

    private fun calculateDistance(a: LocationPoint, b: LocationPoint): Double {
        val result = FloatArray(1)
        Location.distanceBetween(a.latitude, a.longitude, b.latitude, b.longitude, result)
        return result[0] / 1000.0 // convert meters → km
    }

}