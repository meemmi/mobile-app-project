package com.example.pawtracker.ui.history

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class HistoryViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(HistoryUiState())
    val uiState: StateFlow<HistoryUiState> = _uiState
    /* :will be replace with db:
    init {
        loadFakeHistory()
    }

    private fun loadFakeHistory() {
        val fake = listOf(
            WalkUiModel(1, "Today", 2.8, 50),
            WalkUiModel(2, "Yesterday", 3.1, 55),
            WalkUiModel(3, "Apr 22", 2.5, 45),
            WalkUiModel(4, "Apr 21", 3.7, 60)
        )
        _uiState.value = _uiState.value.copy(walks = fake)
    }  */

    fun setFilter(filter: WalkFilter) {
        _uiState.value = _uiState.value.copy(filter = filter)
    }
}
