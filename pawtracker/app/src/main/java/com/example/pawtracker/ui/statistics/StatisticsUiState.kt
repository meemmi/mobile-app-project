package com.example.pawtracker.ui.statistics

// For testing
data class StatisticsUiState(
    val todayDistance: Float = 0f,
    val todayDuration: Long = 0L,
    val weekDistance: Float = 0f,
    val weekDuration: Long = 0L,
    val goalDistance: Double = 0.0,
    val goalDuration: Long = 0L,
    val dogName: String = "",
    val imageUri: String = ""

)