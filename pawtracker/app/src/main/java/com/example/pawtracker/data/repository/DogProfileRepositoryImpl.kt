package com.example.pawtracker.data.repository

import com.example.pawtracker.data.local.DogProfileDao
import com.example.pawtracker.data.local.DogProfileEntity
/**
 * Repository implementation for dog profile data.
 * Uses the DAO to read the profile as a Flow and save updates to the database.
 */
class DogProfileRepositoryImpl(
    private val dao: DogProfileDao
) : DogProfileRepository {

   override fun getProfile() = dao.getProfile()

    override suspend fun saveProfile(profile: DogProfileEntity) {
        dao.upsertProfile(profile)
    }
}
