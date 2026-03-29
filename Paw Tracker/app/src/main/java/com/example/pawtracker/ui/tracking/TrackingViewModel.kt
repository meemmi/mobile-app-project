package com.example.pawtracker.ui.tracking
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pawtracker.data.location.LocationPoint
import com.example.pawtracker.data.repository.GPSRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


//    ViewModel doesn’t know about FusedLocationProviderClient/It only talks to GPSRepository and exposes StateFlow to UI
class TrackingViewModel(
    private val gpsRepository: GPSRepository
) : ViewModel() {

    private val _currentLocation = MutableStateFlow<LocationPoint?>(null)
    val currentLocation: StateFlow<LocationPoint?> = _currentLocation

    fun startTracking() {
        gpsRepository.startLocationUpdates { point ->
            viewModelScope.launch {
                _currentLocation.value = point
            }
        }
    }

    fun stopTracking() {
        gpsRepository.stopLocationUpdates()
    }

    fun loadLastLocation() {
        gpsRepository.getLastLocation { point ->
            viewModelScope.launch {
                _currentLocation.value = point
            }
        }
    }
}
