package com.example.pawtracker.ui.editprofile

data class EditProfileUiState(
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