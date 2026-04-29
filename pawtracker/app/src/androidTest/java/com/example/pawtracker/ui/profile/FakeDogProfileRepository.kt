package com.example.pawtracker.ui.profile

import com.example.pawtracker.data.local.DogProfileEntity
import com.example.pawtracker.data.repository.DogProfileRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeDogProfileRepository(
    private val profile: DogProfileEntity?
) : DogProfileRepository {

    override fun getProfile(): Flow<DogProfileEntity?> =
        flowOf(profile)

    override suspend fun saveProfile(profile: DogProfileEntity) {

    }
}
