package com.example.pawtracker.data.mapper

import com.example.pawtracker.data.local.WalkEntity
import com.example.pawtracker.ui.history.WalkUiModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun WalkEntity.toUiModel(): WalkUiModel {
    return WalkUiModel(
        id = this.id.toLong(),
        date = SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(Date(startTime)),
        distanceKm = this.distance / 1000.0,
        timeMinutes = this.duration / 60000.toInt(),
        startTime = this.startTime
    )
}
