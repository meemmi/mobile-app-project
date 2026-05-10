package com.example.pawtracker.ui.history
/**
 * UI state for the History screen.
 * Holds the list of walks and the currently selected daily/weekly filter.
 */

data class HistoryUiState(
    val walks: List<WalkUiModel> = emptyList(),
    val filter: WalkFilter = WalkFilter.Daily
)
