package com.example.pawtracker.data.local
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * Room entity representing a single recorded walk.
 * Stores start/end timestamps, distance, duration, point count,
 * and an optional preview polyline for map thumbnails.
 */
@Entity(tableName = "walks", indices = [Index("startTime")])
data class WalkEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val startTime: Long,
    val endTime: Long,
    val distance: Float,
    val duration: Long,
    val pointCount: Int,
    val previewPolyline: String? = null
)

