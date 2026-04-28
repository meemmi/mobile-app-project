package com.example.pawtracker.ui.statistics

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.pawtracker.data.repository.DogProfileRepository
import com.example.pawtracker.data.repository.WalkRepository

class StatisticsViewModelFactory(
    private val walkRepository: WalkRepository,
    private val profileRepository: DogProfileRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(StatisticsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return StatisticsViewModel(walkRepository, profileRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
