package com.example.pawtracker.ui.history

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import androidx.lifecycle.viewModelScope
import com.example.pawtracker.data.repository.WalkRepository
import com.example.pawtracker.data.mapper.toUiModel
import com.example.pawtracker.utils.TimeUtils
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.util.Calendar

class HistoryViewModel(
    private val repository: WalkRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(HistoryUiState())
    val uiState: StateFlow<HistoryUiState> = _uiState

    // Cache all walks from DB so filtering is instant
    private var allWalks: List<WalkUiModel> = emptyList()

    init {
        loadHistory()
    }

    private fun loadHistory() {
        viewModelScope.launch {
            repository.getAllWalks()
                .map { list -> list.map { it.toUiModel() } }
                .collect { uiList ->

                    allWalks = uiList

                    val currentFilter = _uiState.value.filter

                    val filtered = when (currentFilter) {
                        WalkFilter.Daily -> filterDaily(allWalks)
                        WalkFilter.Weekly -> filterWeekly(allWalks)
                    }

                    _uiState.value = _uiState.value.copy(
                        walks = filtered
                    )
                }
        }
    }

    fun setFilter(filter: WalkFilter) {
        val filtered = when (filter) {
            WalkFilter.Daily -> filterDaily(allWalks)
            WalkFilter.Weekly -> filterWeekly(allWalks)
        }

        _uiState.value = _uiState.value.copy(
            filter = filter,
            walks = filtered
        )
    }



    private fun applyFilter(filter: WalkFilter) {
        val filtered = when (filter) {
            WalkFilter.Daily -> filterDaily(allWalks)
            WalkFilter.Weekly -> filterWeekly(allWalks)
        }

        _uiState.value = _uiState.value.copy(walks = filtered)
    }


   /* private fun filterDaily(list: List<WalkUiModel>): List<WalkUiModel> {
        val cal = Calendar.getInstance()
        val todayYear = cal.get(Calendar.YEAR)
        val todayDay = cal.get(Calendar.DAY_OF_YEAR)

        return list.filter { walk ->
            cal.timeInMillis = walk.startTime
            cal.get(Calendar.YEAR) == todayYear &&
                    cal.get(Calendar.DAY_OF_YEAR) == todayDay
        }
    }


    private fun filterWeekly(list: List<WalkUiModel>): List<WalkUiModel> {
        val now = System.currentTimeMillis()
        val oneWeekAgo = now - (7 * 24 * 60 * 60 * 1000L)

        return list.filter { walk ->
            walk.startTime in oneWeekAgo..now
        }
    }*/

    private fun filterDaily(list: List<WalkUiModel>): List<WalkUiModel> {
        val startOfDay = TimeUtils.getStartOfDay()
        return list.filter { walk ->
            walk.startTime >= startOfDay
        }
    }
    private fun filterWeekly(list: List<WalkUiModel>): List<WalkUiModel> {
        val startOfWeek = TimeUtils.getStartOfWeek()
        return list.filter { walk ->
            walk.startTime >= startOfWeek
        }
    }


}
