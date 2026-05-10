package com.example.pawtracker.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
/**
 * Room entity storing the dog's profile.
 * Contains basic info (name, breed, age, size) and daily/weekly activity goals.
 */
@Entity(tableName = "dog_profile")
data class DogProfileEntity(
    @PrimaryKey val id: Int = 0,
    val imageUri: String = "",
    val name: String = "",
    val breed: String = "",

    val age: String = "",
    val height: String = "",
    val weight: String = "",

    val dailyDistanceGoal: Float = 0.0f,
    val dailyDurationGoal: Long = 0L,

    val weeklyDistanceGoal: Float = 0.0f,
    val weeklyDurationGoal: Long = 0L
)
