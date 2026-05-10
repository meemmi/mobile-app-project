package com.example.pawtracker.ui.statistics

import com.example.pawtracker.data.local.DogProfileEntity
import com.example.pawtracker.data.repository.DogProfileRepository
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.Flow

/**
 * Fake repository for UI tests.
 * Always returns the provided dog profile and ignores save operations.
 */
class FakeDogProfileRepository(
    private val profile: DogProfileEntity
) : DogProfileRepository {

    override fun getProfile(): Flow<DogProfileEntity?> = flowOf(profile)

    override suspend fun saveProfile(profile: DogProfileEntity) {
        // No-op for tests
    }
}
