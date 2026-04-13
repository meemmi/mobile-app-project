package com.example.pawtracker.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface DogProfileDao {

    @Query("SELECT * FROM dog_profile WHERE id = 0")
    fun getProfile(): Flow<DogProfileEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertProfile(profile: DogProfileEntity)
}
