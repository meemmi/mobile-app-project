package com.example.pawtracker.ui.statistics
import com.example.pawtracker.data.local.DogProfileEntity

fun fakeStatisticsProfile() = DogProfileEntity(
    id = 0,
    imageUri = "",
    name = "Buddy",
    breed = "Golden Retriever",
    age = "5",
    height = "60",
    weight = "30",
    dailyDurationGoal = 45L,
    dailyDistanceGoal = 3.5f,
    weeklyDurationGoal = 300L,
    weeklyDistanceGoal = 20f
)