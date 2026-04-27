package com.example.pawtracker.ui.tracking

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pawtracker.data.local.WalkEntity
import com.example.pawtracker.data.repository.WalkRepository
import com.example.pawtracker.model.LocationPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import com.example.pawtracker.data.repository.GPSRepository
import kotlin.math.*



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
    private var useMockLocation = true
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
       _uiState.value = TrackingUiState(tracking = true)

       startTime = System.currentTimeMillis()
       lastPoint = null

       trackingJob?.cancel()
       trackingJob = viewModelScope.launch {

           if (useMockLocation) {
               mockLocationFlow().collect { handleNewPoint(it) }
           } else {
               gpsRepository.startLocationUpdates { point ->
                   handleNewPoint(point)
               }
           }
       }
   }

    private fun handleNewPoint(point: LocationPoint) {
        val addedDistance = if (lastPoint != null) {
            calculateDistance(lastPoint!!, point)
        } else 0f

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
            distance = (_uiState.value.distance * 1000),
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


    private fun calculateDistance(a: LocationPoint, b: LocationPoint): Float {
        val r = 6371e3 // meters
        val lat1 = Math.toRadians(a.latitude)
        val lat2 = Math.toRadians(b.latitude)
        val dLat = Math.toRadians(b.latitude - a.latitude)
        val dLon = Math.toRadians(b.longitude - a.longitude)

        val h = sin(dLat / 2).pow(2.0) +
                cos(lat1) * cos(lat2) *
                sin(dLon / 2).pow(2.0)

        val c = 2 * atan2(sqrt(h), sqrt(1 - h))

        return (r * c / 1000f).toFloat() // km
    }

}


