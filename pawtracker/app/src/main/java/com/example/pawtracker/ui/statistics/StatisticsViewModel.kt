package com.example.pawtracker.ui.statistics

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pawtracker.data.local.DogProfileDao
import com.example.pawtracker.data.local.DogProfileEntity
import com.example.pawtracker.data.repository.DogProfileRepository
import com.example.pawtracker.data.repository.WalkRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class StatisticsViewModel(
    private val walkRepository: WalkRepository,
    private val profileRepository: DogProfileRepository
) : ViewModel() {

    // Fetch walk statistics as individual flows
    private val todayDistance = walkRepository.getTodayDistance().map { it ?: 0f }
    private val todayDuration = walkRepository.getTodayDuration().map { it ?: 0L }
    private val weekDistance = walkRepository.getWeekDistance().map { it ?: 0f }
    private val weekDuration = walkRepository.getWeekDuration().map { it ?: 0L }

    // Fetch profile to show picture, name, goals
    private val getProfile = profileRepository.getProfile().map { profile ->
        profile ?: DogProfileEntity()
    }

    val uiState: StateFlow<StatisticsUiState> =
        combine(
            todayDistance,
            todayDuration,
            weekDistance,
            weekDuration,
            getProfile
        ) { todayDist, todayDur, weekDist, weekDur, profile ->

            StatisticsUiState(
                todayDistance = todayDist,
                todayDuration = todayDur,
                weekDistance = weekDist,
                weekDuration = weekDur,
                goalDistance = profile.dailyDistanceGoal,
                goalDuration = profile.dailyDurationGoal
            )
        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            StatisticsUiState()
        )

}