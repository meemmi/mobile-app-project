package com.example.pawtracker.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "dog_profile")
data class DogProfileEntity(
    @PrimaryKey val id: Int = 0,
    val imageUri: String = "",
    val name: String = "",
    val breed: String = "",

    val dailyDistanceGoal: Double = 0.0,
    val dailyDurationGoal: Long = 0L,

    val weeklyDistanceGoal: Double = 0.0,
    val weeklyDurationGoal: Long = 0L
)
