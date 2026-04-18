package com.example.pawtracker.data.repository

import com.example.pawtracker.data.local.WalkDao
import com.example.pawtracker.data.local.WalkEntity
import com.example.pawtracker.data.local.WalkWithPoints
import com.example.pawtracker.data.local.GpsPointEntity
import com.example.pawtracker.model.LocationPoint
import com.example.pawtracker.utils.TimeUtils

class WalkRepositoryImpl(
    private val dao: WalkDao
) : WalkRepository {

    override fun getAllWalks() = dao.getAllWalks()

    override fun getTodayWalks() =
        dao.getWalksFromDay(TimeUtils.getStartOfDay())

    override fun getWalksByWeek() =
        dao.getWalksFromWeek(TimeUtils.getStartOfWeek())

    override suspend fun getWalkDetails(walkId: Long): WalkWithPoints {
        return dao.getWalkWithPoints(walkId)
    }

    // Statistics
    override fun getTodayDistance() =
        dao.getTotalDistanceSince(TimeUtils.getStartOfDay())

    override fun getTodayDuration() =
        dao.getTotalDurationSince(TimeUtils.getStartOfDay())

    override fun getWeekDistance() =
        dao.getTotalDistanceSince(TimeUtils.getStartOfWeek())

    override fun getWeekDuration() =
        dao.getTotalDurationSince(TimeUtils.getStartOfWeek())

    override suspend fun insertWalkWithPoints(
        walk: WalkEntity,
        points: List<LocationPoint>
    ) {
        // 1. Insert parent row
        val walkId = dao.insertWalk(walk)

        // 2. Convert to DB entities
        val pointEntities = points.mapIndexed { index, point ->
            GpsPointEntity(
                walkId = walkId,
                sequence = index,
                timestamp = point.time,
                latitude = point.latitude,
                longitude = point.longitude
            )
        }

        // 3. Insert all points
        dao.insertPoints(pointEntities)
    }


    override suspend fun deleteWalk(walk: WalkEntity) {
        dao.deleteWalk(walk)
    }
}
