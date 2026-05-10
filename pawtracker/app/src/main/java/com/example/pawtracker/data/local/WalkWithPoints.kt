package com.example.pawtracker.data.local

import androidx.room.Embedded
import androidx.room.Relation
/**
 * Combined model of a walk and all its GPS points.
 * Uses Room's @Relation to load the parent walk together with
 * its ordered list of recorded GPS coordinates.
 */
data class WalkWithPoints(
    @Embedded val walk: WalkEntity,

    @Relation(
        parentColumn = "id",
        entityColumn = "walkId"
    )
    val points: List<GpsPointEntity>
)