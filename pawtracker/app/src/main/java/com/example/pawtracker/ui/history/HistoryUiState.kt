package com.example.pawtracker.ui.history

data class HistoryUiState(
    val walks: List<WalkUiModel> = emptyList(),
    val filter: WalkFilter = WalkFilter.Daily
)
