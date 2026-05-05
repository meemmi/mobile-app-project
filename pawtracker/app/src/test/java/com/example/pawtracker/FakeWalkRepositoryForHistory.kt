package com.example.pawtracker

import com.example.pawtracker.data.local.WalkEntity
import com.example.pawtracker.data.local.WalkWithPoints
import com.example.pawtracker.data.repository.WalkRepository
import com.example.pawtracker.model.LocationPoint
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlin.collections.emptyList

class FakeWalkRepositoryForHistory(
    private val walks: List<WalkEntity> = emptyList()
) : WalkRepository {

    override fun getAllWalks(): Flow<List<WalkEntity>> =
        flowOf(walks)

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

    override suspend fun insertWalkWithPoints(walk: WalkEntity, points: List<LocationPoint>) {}
    override suspend fun deleteWalk(walk: WalkEntity) {}
}
