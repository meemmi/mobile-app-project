package com.example.pawtracker

import com.example.pawtracker.data.local.WalkEntity
import com.example.pawtracker.data.local.WalkWithPoints
import com.example.pawtracker.model.LocationPoint
import com.example.pawtracker.data.repository.WalkRepository
import kotlinx.coroutines.flow.flowOf
/**
 * Fake WalkRepository for statistics tests.
 * Provides fixed distance and duration values for today and this week,
 * while all history and write operations return empty or no-op.
 */
class FakeWalkRepositoryForStatistics : WalkRepository {

    override fun getTodayDistance() = flowOf(2000f)   // 2 km
    override fun getTodayDuration() = flowOf(100L)

    override fun getWeekDistance() = flowOf(10000f)   // 10 km
    override fun getWeekDuration() = flowOf(500L)

    override fun getAllWalks() = flowOf(emptyList<WalkEntity>())
    override fun getTodayWalks() = flowOf(emptyList<WalkEntity>())
    override fun getWalksByWeek() = flowOf(emptyList<WalkEntity>())

    override suspend fun getWalkDetails(walkId: Long) =
        WalkWithPoints(
            walk = WalkEntity(
                id = walkId,
                startTime = 0L,
                endTime = 0L,
                distance = 0f,
                duration = 0L,
                pointCount = 0
            ),
            points = emptyList()
        )

    override suspend fun insertWalkWithPoints(
        walk: WalkEntity,
        points: List<LocationPoint>
    ) {}

    override suspend fun deleteWalk(walk: WalkEntity) {}
}


