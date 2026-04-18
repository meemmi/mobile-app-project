package com.example.pawtracker

import com.example.pawtracker.data.local.DogProfileEntity
import com.example.pawtracker.data.repository.DogProfileRepository
import kotlinx.coroutines.flow.flowOf

class FakeDogProfileRepository : DogProfileRepository {

    override fun getProfile() = flowOf(
        DogProfileEntity(
            name = "Nelli",
            imageUri = "testUri",
            dailyDistanceGoal = 5f,
            dailyDurationGoal = 30L,
            weeklyDistanceGoal = 20f,
            weeklyDurationGoal = 120L
        )
    )

    override suspend fun saveProfile(profile: DogProfileEntity) {}
}

