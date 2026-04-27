package com.example.pawtracker

import com.example.pawtracker.data.local.WalkEntity
import com.example.pawtracker.data.local.WalkWithPoints
import com.example.pawtracker.data.repository.WalkRepository
import com.example.pawtracker.model.LocationPoint
import kotlinx.coroutines.flow.flowOf
import kotlin.collections.emptyList

/*class FakeWalkRepository : WalkRepository {

    override fun getTodayDistance() = flowOf(2f)
    override fun getTodayDuration() = flowOf(100L)
    override fun getWeekDistance() = flowOf(10f)
    override fun getWeekDuration() = flowOf(500L)

    override fun getAllWalks() = flowOf(emptyList<WalkEntity>())

    override fun getTodayWalks() = flowOf(emptyList<WalkEntity>())

    override fun getWalksByWeek() = flowOf(emptyList<WalkEntity>())

    override suspend fun getWalkDetails(walkId: Long): WalkWithPoints {
        return WalkWithPoints(
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
    }

    override suspend fun insertWalkWithPoints(
        walk: WalkEntity,
        points: List<LocationPoint>
    ) {

    }
    override suspend fun deleteWalk(walk: WalkEntity) {
    }
}*/
class FakeWalkRepository(
    private val walks: List<WalkEntity> = emptyList()
) : WalkRepository {

    var savedWalk: WalkEntity? = null
    var savedPoints: List<LocationPoint> = emptyList()

    override fun getAllWalks() = flowOf(walks)

    override fun getTodayWalks() = flowOf(walks)

    override fun getWalksByWeek() = flowOf(walks)

    override fun getTodayDistance() = flowOf(2f)
    override fun getTodayDuration() = flowOf(100L)
    override fun getWeekDistance() = flowOf(10f)
    override fun getWeekDuration() = flowOf(500L)

    override suspend fun getWalkDetails(walkId: Long): WalkWithPoints {
        return WalkWithPoints(
            walk = walks.firstOrNull() ?: WalkEntity(
                id = walkId,
                startTime = 0L,
                endTime = 0L,
                distance = 0f,
                duration = 0L,
                pointCount = 0
            ),
            points = emptyList()
        )
    }

    override suspend fun insertWalkWithPoints(
        walk: WalkEntity,
        points: List<LocationPoint>
    ) {
        savedWalk = walk
        savedPoints = points
    }

    override suspend fun deleteWalk(walk: WalkEntity) {}
}
