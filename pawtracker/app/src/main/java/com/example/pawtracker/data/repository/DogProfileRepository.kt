package com.example.pawtracker.data.repository

import com.example.pawtracker.data.local.DogProfileEntity
import kotlinx.coroutines.flow.Flow
/**
 * Repository interface for loading and saving the dog profile.
 * Exposes a Flow for observing profile changes and a suspend function for updates.
 */
interface DogProfileRepository {
    fun getProfile(): Flow<DogProfileEntity?>
    suspend fun saveProfile(profile: DogProfileEntity)
}