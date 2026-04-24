package com.example.pawtracker.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import android.content.Context
import androidx.room.Room


@Database(entities = [WalkEntity::class, GpsPointEntity::class, DogProfileEntity::class], version = 5)
abstract class AppDatabase : RoomDatabase() {
    abstract fun walkDao(): WalkDao
    abstract fun dogProfileDao(): DogProfileDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "pawtracker_db"
                )
                    .addMigrations(MIGRATION_1_2)
                    .fallbackToDestructiveMigration(true)
                    .build()

                INSTANCE = instance
                instance
            }
        }
    }
}

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(db: SupportSQLiteDatabase) {

        db.execSQL("""
            CREATE TABLE IF NOT EXISTS gps_points (
                id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                walkId INTEGER NOT NULL,
                sequence INTEGER NOT NULL,
                timestamp INTEGER NOT NULL,
                latitude REAL NOT NULL,
                longitude REAL NOT NULL,
                FOREIGN KEY(walkId) REFERENCES walks(id) ON DELETE CASCADE
            )
        """)

        db.execSQL("ALTER TABLE walks ADD COLUMN previewPolyline TEXT")

        db.execSQL("""
            UPDATE walks SET previewPolyline = path
        """)
    }
}