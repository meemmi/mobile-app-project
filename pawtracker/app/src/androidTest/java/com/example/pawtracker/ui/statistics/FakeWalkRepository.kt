package com.example.pawtracker.ui.statistics

import com.example.pawtracker.data.repository.WalkRepository
import kotlinx.coroutines.flow.flowOf
import com.example.pawtracker.model.LocationPoint
import com.example.pawtracker.data.local.WalkEntity
import com.example.pawtracker.data.local.WalkWithPoints
import kotlinx.coroutines.flow.Flow


class FakeWalkRepository(
    private val todayDistance: Float,
    private val todayDuration: Long,
    private val weekDistance: Float,
    private val weekDuration: Long
) : WalkRepository {

    // -------- STATISTICS VALUES --------
    override fun getTodayDistance(): Flow<Float> = flowOf(todayDistance)
    override fun getTodayDuration(): Flow<Long> = flowOf(todayDuration)
    override fun getWeekDistance(): Flow<Float> = flowOf(weekDistance)
    override fun getWeekDuration(): Flow<Long> = flowOf(weekDuration)

    // -------- UNUSED IN UI TESTS (return empty) --------
    override fun getAllWalks(): Flow<List<WalkEntity>> = flowOf(emptyList())

    override fun getTodayWalks(): Flow<List<WalkEntity>> = flowOf(emptyList())

    override fun getWalksByWeek(): Flow<List<WalkEntity>> = flowOf(emptyList())

    override suspend fun getWalkDetails(walkId: Long): WalkWithPoints {
        return WalkWithPoints(
            walk = WalkEntity(
                id = walkId,
                startTime = 0L,
                endTime = 0L,
                distance = 0f,
                duration = 0L,
                pointCount = 0,
                previewPolyline = ""
            ),
            points = emptyList()
        )
    }

    override suspend fun insertWalkWithPoints(
        walk: WalkEntity,
        points: List<LocationPoint>
    ) {
        // no-op for UI tests
    }

    override suspend fun deleteWalk(walk: WalkEntity) {
        // no-op for UI tests
    }
}