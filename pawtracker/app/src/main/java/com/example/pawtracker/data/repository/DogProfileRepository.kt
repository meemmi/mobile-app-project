package com.example.pawtracker.data.repository

import com.example.pawtracker.data.local.DogProfileEntity
import kotlinx.coroutines.flow.Flow

interface DogProfileRepository {
    fun getProfile(): Flow<DogProfileEntity?>
    suspend fun saveProfile(profile: DogProfileEntity)
}