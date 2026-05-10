package com.example.pawtracker

import com.example.pawtracker.data.local.WalkEntity
import com.example.pawtracker.data.local.WalkWithPoints
import com.example.pawtracker.data.repository.WalkRepository
import com.example.pawtracker.model.LocationPoint
import kotlinx.coroutines.flow.flowOf
/**
 * Fake WalkRepository used in TrackingViewModel tests.
 * Captures the walk and GPS points passed to insertWalkWithPoints,
 * while all read operations return empty or null since tracking
 * only cares about saving data, not loading history or statistics.
 */
class FakeWalkRepositoryForTracking : WalkRepository {

    var savedWalk: WalkEntity? = null
    var savedPoints: List<LocationPoint> = emptyList()

    // Not used in Tracking tests
    override fun getAllWalks() = flowOf(emptyList<WalkEntity>())
    override fun getTodayWalks() = flowOf(emptyList<WalkEntity>())
    override fun getWalksByWeek() = flowOf(emptyList<WalkEntity>())

    override fun getTodayDistance() = flowOf(null)
    override fun getTodayDuration() = flowOf(null)
    override fun getWeekDistance() = flowOf(null)
    override fun getWeekDuration() = flowOf(null)

    override suspend fun getWalkDetails(walkId: Long) =
        WalkWithPoints(
            walk = WalkEntity(walkId, 0, 0, 0f, 0L, 0),
            points = emptyList()
        )

    override suspend fun insertWalkWithPoints(
        walk: WalkEntity,
        points: List<LocationPoint>
    ) {
        savedWalk = walk
        savedPoints = points
    }

    override suspend fun deleteWalk(walk: WalkEntity) {}
}