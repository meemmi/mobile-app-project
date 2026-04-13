package com.example.pawtracker.ui.profile

// For testing
data class ProfileUiState(
    val imageUri: String = "",
    val name: String = "",
    val breed: String = "",
    val dailyDistanceGoal: String = "",
    val dailyDurationGoal: String = "",

    val weeklyDistanceGoal: String = "",
    val weeklyDurationGoal: String = "",
    val isSaved: Boolean = false
)
