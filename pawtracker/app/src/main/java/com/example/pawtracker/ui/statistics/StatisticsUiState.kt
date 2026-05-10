package com.example.pawtracker.ui.statistics
/**
 * UI state for the Statistics screen.
 * Holds today's and week's walk data, daily goals, and dog profile info.
 */

data class StatisticsUiState(
    val todayDistance: Float = 0f,
    val todayDuration: Long = 0L,
    val weekDistance: Float = 0f,
    val weekDuration: Long = 0L,
    val goalDistance: Float = 0.0f,
    val goalDuration: Long = 0L,
    val dogName: String = "",
    val imageUri: String = ""
)