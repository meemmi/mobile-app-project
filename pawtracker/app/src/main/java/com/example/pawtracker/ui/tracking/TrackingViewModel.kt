package com.example.pawtracker.ui.tracking

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pawtracker.data.local.WalkEntity
import com.example.pawtracker.data.repository.WalkRepository
import com.example.pawtracker.data.repository.GPSRepository
import com.example.pawtracker.model.LocationPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
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

    //a reference to the coroutine that is collecting GPS updates:trackingjob
    private var trackingJob: Job? = null
    private var useMockLocation = false
    private var startTime: Long = 0L
    private var lastPoint: LocationPoint? = null


    // MOCK FLOW FOR TESTING
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
            delay(1000)
            emit(point)
        }
    }

    fun startTracking() {
        // Reset UI
        _uiState.value = TrackingUiState(tracking = true)

        startTime = System.currentTimeMillis()
        lastPoint = null

        trackingJob?.cancel()
        trackingJob = viewModelScope.launch {


            if (useMockLocation) {
                mockLocationFlow().collect { point ->
                    handleNewPoint(point)
                }
            } else {
                gpsRepository.startLocationUpdates { point ->
                    handleNewPoint(point)
                }
            }
        }

    }

    private fun handleNewPoint(point: LocationPoint) {
        if (lastPoint != null) {

            val distance = calculateDistance(lastPoint!!, point)

            //  Ignore GPS jumps (> 100 meters in 1 second)
            if (distance > 0.1) return   // 0.1 km = 100 meters

            // Ignore very tiny noise (< 3 meters)
            if (distance < 0.003) return
        }




        val addedDistance = if (lastPoint != null) {
            calculateDistance(lastPoint!!, point)
        } else 0.0

        lastPoint = point
        val elapsedTime = System.currentTimeMillis() - startTime

        _uiState.update { state ->
            state.copy(
                currentLocation = point,
                points = state.points + point,
                distance = state.distance + addedDistance,
                time = elapsedTime
            )
        }
    }


    fun stopTracking() {
        _uiState.update { it.copy(tracking = false) }

        trackingJob?.cancel()
        trackingJob = null

        if (!useMockLocation) {
            gpsRepository.stopLocationUpdates()
        }

        val endTime = System.currentTimeMillis()

        val points = _uiState.value.points

        val walk = WalkEntity(
            startTime = startTime,
            endTime = endTime,
            distance = (_uiState.value.distance * 1000).toFloat(),
            duration = endTime - startTime,
            pointCount = points.size,
            previewPolyline = null
        )

        viewModelScope.launch {
            walkRepository.insertWalkWithPoints(
                walk = walk,
                points = points
            )
        }

        // Reset UI after saving
        _uiState.value = TrackingUiState()
    }


    fun loadLastLocation() {
        if (!useMockLocation) {
            gpsRepository.getLastLocation { point ->
                viewModelScope.launch {
                    _uiState.update { it.copy(currentLocation = point) }
                }
            }
        }
    }

    fun setUseMockLocation(enable: Boolean) {
        useMockLocation = enable
    }

    // DISTANCE CALCULATION

    private fun calculateDistance(a: LocationPoint, b: LocationPoint): Double {
        val result = FloatArray(1)
        Location.distanceBetween(a.latitude, a.longitude, b.latitude, b.longitude, result)
        return result[0] / 1000.0 // meters → km
    }
}