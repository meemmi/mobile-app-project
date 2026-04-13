package com.example.pawtracker.ui.statistics

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pawtracker.data.repository.WalkRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class StatisticsViewModel(
    private val repository: WalkRepository,
   // private val profileDao: DogProfileDao
) : ViewModel() {

    // For testing
    init {
        viewModelScope.launch {
            repository.getTodayDistance().collect {
                Log.d("statistics", "Today distance = $it")
            }
        }

        viewModelScope.launch {
            repository.getTodayDuration().collect {
                Log.d("statistics", "Today duration = $it")
            }
        }

        viewModelScope.launch {
            repository.getWeekDistance().collect {
                Log.d("statistics", "Week distance = $it")
            }
        }

        viewModelScope.launch {
            repository.getWeekDuration().collect {
                Log.d("statistics", "Week duration = $it")
            }
        }
    }

    private val todayDistance =
        repository.getTodayDistance().map { it ?: 0f }

    private val todayDuration =
        repository.getTodayDuration().map { it ?: 0L }

    private val weekDistance =
        repository.getWeekDistance().map { it ?: 0f }

    private val weekDuration =
        repository.getWeekDuration().map { it ?: 0L }

    /*
    private val goals =
        profileDao.getProfile()
            .map {
                it?.let { p ->
                    p.dailyDistanceGoalKm to p.dailyDurationGoalMs
                } ?: (0.0 to 0L)
            }

*/

    val uiState: StateFlow<StatisticsUiState> =
        combine(
            todayDistance,
            todayDuration,
            weekDistance,
            weekDuration,
          //  goals
        ) { todayDist, todayDur, weekDist, weekDur, -> //(goalDist, goalDur)

            StatisticsUiState(
                todayDistance = todayDist,
                todayDuration = todayDur,
                weekDistance = weekDist,
                weekDuration = weekDur,
                goalDistance = 0.0, //goalDist,
                goalDuration = 0L //goalDur
            )
        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            StatisticsUiState()
        )

}