package com.example.pawtracker.data.repository

import com.example.pawtracker.data.local.WalkDao
import com.example.pawtracker.data.local.WalkEntity
import com.example.pawtracker.data.local.WalkWithPoints
import com.example.pawtracker.data.local.GpsPointEntity
import com.example.pawtracker.model.LocationPoint
import com.example.pawtracker.utils.TimeUtils
import kotlinx.coroutines.flow.map

class WalkRepository(private val dao: WalkDao) {

    fun getAllWalks() = dao.getAllWalks()

    fun getTodayWalks() =
        dao.getWalksFromDay(TimeUtils.getStartOfDay())

    fun getWalksByWeek() =
        dao.getWalksFromWeek(TimeUtils.getStartOfWeek())

    suspend fun getWalkDetails(walkId: Long): WalkWithPoints {
        return dao.getWalkWithPoints(walkId)
    }

    // Statistics

    fun getTodayDistance() =
        dao.getDailyDistance(TimeUtils.getStartOfDay())
            .map { (it ?: 0f) / 1000f } // meters → km

    fun getTodayDuration() =
        dao.getDailyDuration(TimeUtils.getStartOfDay())
            .map { it ?: 0L } // ms

    fun getWeekDistance() =
        dao.getWeeklyDistance(TimeUtils.getStartOfWeek())
            .map { (it ?: 0f) / 1000f } // meters → km

    fun getWeekDuration() =
        dao.getWeeklyDuration(TimeUtils.getStartOfWeek())
            .map { it ?: 0L } // ms


    suspend fun insertWalkWithPoints(
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


    suspend fun deleteWalk(walk: WalkEntity) {
        dao.deleteWalk(walk)
    }
}
