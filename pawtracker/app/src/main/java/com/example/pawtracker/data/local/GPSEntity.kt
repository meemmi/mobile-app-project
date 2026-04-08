package com.example.pawtracker.data.local

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "gps_points",
    foreignKeys = [
        ForeignKey(
            entity = WalkEntity::class,
            parentColumns = ["id"],
            childColumns = ["walkId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("walkId")]
)


data class GpsPointEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val walkId: Long, // WalkEntity

    val latitude: Double,
    val longitude: Double,
    val timestamp: Long,

    val orderIndex: Int // Order of the points
)