package com.example.pawtracker.data.repository

import com.example.pawtracker.data.local.WalkDao
import com.example.pawtracker.data.local.WalkEntity
import com.example.pawtracker.utils.TimeUtils

class WalkRepository(private val dao: WalkDao) {

    fun getAllWalks() = dao.getAllWalks()

    fun getTodayWalks() =
        dao.getWalksFromDay(TimeUtils.getStartOfDay())

    fun getWalksByWeek() =
        dao.getWalksFromWeek(TimeUtils.getStartOfWeek())

    suspend fun insertWalk(walk: WalkEntity) {
        dao.insertWalk(walk)
    }

    suspend fun deleteWalk(walk: WalkEntity) {
        dao.deleteWalk(walk)
    }
}
