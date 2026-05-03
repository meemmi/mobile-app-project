package com.example.pawtracker

import android.app.Application
import androidx.room.Room
import com.example.pawtracker.data.local.AppDatabase
import com.example.pawtracker.data.local.preferences.PreferenceRepository
import com.example.pawtracker.data.repository.DogProfileRepositoryImpl
import com.example.pawtracker.data.repository.GPSRepositoryImpl
import com.example.pawtracker.data.repository.WalkRepository
import com.example.pawtracker.data.repository.WalkRepositoryImpl

class PawTrackerApplication : Application() {
    private val database by lazy {
        Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "walk_db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    val gpsRepository by lazy { GPSRepositoryImpl(applicationContext) }


    val walkRepository: WalkRepository by lazy {
        WalkRepositoryImpl(database.walkDao())
    }

    val dogProfileRepository: DogProfileRepositoryImpl by lazy {
        DogProfileRepositoryImpl(database.dogProfileDao())
    }

    val preferenceRepository by lazy { PreferenceRepository(applicationContext) }
}