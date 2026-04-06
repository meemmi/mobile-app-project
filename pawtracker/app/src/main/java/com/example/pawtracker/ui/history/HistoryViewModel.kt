package com.example.pawtracker.ui.history

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

import androidx.lifecycle.viewModelScope
import com.example.pawtracker.data.repository.WalkRepository
import com.example.pawtracker.ui.history.HistoryUiState
import com.example.pawtracker.ui.history.WalkFilter
import com.example.pawtracker.data.mapper.toUiModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class HistoryViewModel(
    private val repository: WalkRepository
) : ViewModel() {


    private val _uiState = MutableStateFlow(HistoryUiState())
    val uiState: StateFlow<HistoryUiState> = _uiState
    init {
        loadHistory()
    }

    private fun loadHistory() {
        viewModelScope.launch {
            repository.getAllWalks()
                .map { list -> list.map { it.toUiModel() } }
                .collect { uiList ->
                    _uiState.value = _uiState.value.copy(walks = uiList)
                }
        }
    }

    fun setFilter(filter: WalkFilter) {
        _uiState.value = _uiState.value.copy(filter = filter)
    }
}
