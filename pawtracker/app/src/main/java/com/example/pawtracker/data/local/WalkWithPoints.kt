package com.example.pawtracker.data.local

import androidx.room.Embedded
import androidx.room.Relation

data class WalkWithPoints(
    @Embedded val walk: WalkEntity,

    @Relation(
        parentColumn = "id",
        entityColumn = "walkId"
    )
    val points: List<GpsPointEntity>
)