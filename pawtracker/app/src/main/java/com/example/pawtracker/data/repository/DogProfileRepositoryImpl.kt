package com.example.pawtracker.data.repository

import com.example.pawtracker.data.local.DogProfileDao
import com.example.pawtracker.data.local.DogProfileEntity

class DogProfileRepositoryImpl(
    private val dao: DogProfileDao
) : DogProfileRepository {

   override fun getProfile() = dao.getProfile()

    override suspend fun saveProfile(profile: DogProfileEntity) {
        dao.upsertProfile(profile)
    }
}
