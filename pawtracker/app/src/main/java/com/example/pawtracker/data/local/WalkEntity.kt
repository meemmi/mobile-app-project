package com.example.pawtracker.data.local
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.pawtracker.model.LocationPoint

/**
 * Room database entity for a single recorded walk
 */
@Entity(tableName = "walks")
data class WalkEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0, // Randomly generated id for each walk
    val startTime: Long, // Milliseconds epoch
    val endTime: Long, // Milliseconds epoch
    val distance: Float, // Meters
    val duration: Long, // Milliseconds
    val polyline: String? // Encoded polyline for Google Maps format
    //val path: List<LocationPoint> // Ordered list of GPS coordinates representing the route
)

