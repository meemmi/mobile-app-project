package com.example.pawtracker.data.mapper

import com.example.pawtracker.data.local.WalkEntity
import com.example.pawtracker.ui.history.WalkUiModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
/**
 * Converts a WalkEntity into a UI-friendly model.
 * Formats the date, converts distance to km, and duration to minutes.
 */
fun WalkEntity.toUiModel(): WalkUiModel {
    return WalkUiModel(
        id = this.id,
        date = SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(Date(startTime)),
        distanceKm = this.distance / 1000.0,
        timeMinutes = this.duration / 60000,
        startTime = this.startTime
    )
}
