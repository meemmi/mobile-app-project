package com.example.pawtracker.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.pawtracker.data.local.converters.LocationPointConverter

@Database(entities = [WalkEntity::class], version = 1)
@TypeConverters(LocationPointConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun walkDao(): WalkDao
}