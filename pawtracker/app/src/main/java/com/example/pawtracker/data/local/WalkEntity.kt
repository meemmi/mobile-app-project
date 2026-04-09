package com.example.pawtracker.data.local
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.pawtracker.model.LocationPoint

/**
 * Room database entity for a single recorded walk
 */
@Entity(tableName = "walks", indices = [Index("startTime")])
data class WalkEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0, // Randomly generated id for each walk
    val startTime: Long, // Milliseconds epoch
    val endTime: Long, // Milliseconds epoch
    val distance: Float, // Meters
    val duration: Long, // Milliseconds
    val pointCount: Int,
    val previewPolyline: String? = null // Encoded polyline for Google Maps format
)

