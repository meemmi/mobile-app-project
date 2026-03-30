package com.example.pawtracker.ui.tracking

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pawtracker.model.LocationPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
//import com.example.pawtracker.model.LocationPoint
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.scan
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.collections.emptyList

class TrackingViewModel : ViewModel() {

    private val _tracking = MutableStateFlow(false)

    private fun mockLocationFlow(): Flow<LocationPoint> = flow {
        val mockRoute = listOf(
            LocationPoint(60.1699, 24.9384),
            LocationPoint(60.1705, 24.9392),
            LocationPoint(60.1712, 24.9401),
            LocationPoint(60.1720, 24.9410),
            LocationPoint(60.1730, 24.9420)
        )

        for (point in mockRoute) {
            delay(1000)
            emit(point)
        }
    }

    val uiState: StateFlow<TrackingUiState> =
        _tracking
            .flatMapLatest { isTracking ->
                if (!isTracking) {
                    flowOf(TrackingUiState(tracking = false))
                } else {
                    mockLocationFlow()
                        .scan(TrackingUiState(tracking = true)) { state, point ->
                            state.copy(
                                currentLocation = point,
                                points = state.points + point
                            )
                        }
                }
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = TrackingUiState()
            )

    fun startTracking() {
        _tracking.value = true
    }

    fun stopTracking() {
        _tracking.value = false
    }
}

/*
class TrackingViewModel(
    //private val gpsRepository: GPSRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(TrackingUiState())
    val uiState = _uiState.asStateFlow()


    fun startTracking() {
        _uiState.value = _uiState.value.copy(
            tracking = true,
            points = emptyList(),
            currentLocation = null
        )


        val mockRoute = listOf(
            LocationPoint(60.1699, 24.9384),
            LocationPoint(60.1705, 24.9392),
            LocationPoint(60.1712, 24.9401),
            LocationPoint(60.1720, 24.9410),
            LocationPoint(60.1730, 24.9420)
        )

        viewModelScope.launch {
            mockRoute.forEach { point ->
                delay(1000)

                _uiState.value = _uiState.value.copy(
                    currentLocation = point,
                    points = _uiState.value.points + point
                )
            }
        }

    }

    fun stopTracking() {
        _uiState.value = _uiState.value.copy(tracking = false)
    }
}
*/
