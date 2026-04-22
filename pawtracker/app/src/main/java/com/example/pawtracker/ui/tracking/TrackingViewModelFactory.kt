package com.example.pawtracker.ui.tracking

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.pawtracker.data.repository.GPSRepository
import com.example.pawtracker.data.repository.WalkRepository

class TrackingViewModelFactory(
    private val gpsRepository: GPSRepository,
    private val walkRepository: WalkRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TrackingViewModel::class.java)) {
            return TrackingViewModel(gpsRepository, walkRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
