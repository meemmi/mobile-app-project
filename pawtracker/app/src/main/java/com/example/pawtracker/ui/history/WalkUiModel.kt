package com.example.pawtracker.ui.history
/**
 * UI model for displaying a past walk in the History screen.
 * Contains formatted date, distance, duration, and start time for filtering.
 */

data class WalkUiModel(
    val id: Long,
    val date: String,
    val distanceKm: Double,
    val timeMinutes: Long,
    val startTime: Long
)