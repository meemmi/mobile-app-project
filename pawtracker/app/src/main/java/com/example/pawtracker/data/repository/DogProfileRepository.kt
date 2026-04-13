package com.example.pawtracker.data.repository

import com.example.pawtracker.data.local.DogProfileDao
import com.example.pawtracker.data.local.DogProfileEntity

class DogProfileRepository(
    private val dao: DogProfileDao
) {

    fun getProfile() = dao.getProfile()

    suspend fun saveProfile(profile: DogProfileEntity) {
        dao.upsertProfile(profile)
    }
}
