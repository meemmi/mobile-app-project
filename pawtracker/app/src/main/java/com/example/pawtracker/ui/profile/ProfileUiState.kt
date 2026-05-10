package com.example.pawtracker.ui.profile
/**
 * UI state for the Profile screen.
 * Holds all editable dog profile fields, including image, basic info,
 * and daily/weekly activity goals.
 */

data class ProfileUiState(
    val imageUri: String = "",
    val name: String = "",
    val breed: String = "",
    val age: String = "",
    val height: String = "",
    val weight: String = "",
    val dailyDistanceGoal: String = "",
    val dailyDurationGoal: String = "",
    val weeklyDistanceGoal: String = "",
    val weeklyDurationGoal: String = "",
    val isSaved: Boolean = false
)
