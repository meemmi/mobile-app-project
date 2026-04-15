package com.example.pawtracker.data.repository

import com.example.pawtracker.data.local.WalkEntity
import com.example.pawtracker.data.local.WalkWithPoints
import com.example.pawtracker.model.LocationPoint
import kotlinx.coroutines.flow.Flow

interface WalkRepository {
    fun getAllWalks(): Flow<List<WalkEntity>>
    fun getTodayWalks(): Flow<List<WalkEntity>>
    fun getWalksByWeek(): Flow<List<WalkEntity>>

    suspend fun getWalkDetails(walkId: Long): WalkWithPoints

    // Statistics
    fun getTodayDistance(): Flow<Float?>
    fun getTodayDuration(): Flow<Long?>
    fun getWeekDistance(): Flow<Float?>
    fun getWeekDuration(): Flow<Long?>

    suspend fun insertWalkWithPoints(walk: WalkEntity, points: List<LocationPoint>)
    suspend fun deleteWalk(walk: WalkEntity)
    }
