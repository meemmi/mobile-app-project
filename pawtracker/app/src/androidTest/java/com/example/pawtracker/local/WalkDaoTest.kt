package com.example.pawtracker.local

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4

import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import com.example.pawtracker.data.local.AppDatabase
import com.example.pawtracker.data.local.WalkDao
import com.example.pawtracker.data.local.WalkEntity
import com.example.pawtracker.data.local.GpsPointEntity

@RunWith(AndroidJUnit4::class)
class WalkDaoTest {

    private lateinit var db: AppDatabase
    private lateinit var walkDao: WalkDao

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()

        db = Room.inMemoryDatabaseBuilder(
            context,
            AppDatabase::class.java
        )
            .allowMainThreadQueries() // OK for tests
            .build()

        walkDao = db.walkDao()
    }

    @After
    fun teardown() {
        db.close()
    }

    @Test
    fun insertWalk_andReadItBack() = runTest {
        val walk = WalkEntity(
            startTime = 1000L,
            endTime = 2000L,
            distance = 1.5f,
            duration = 1000L,
            pointCount = 2,
            previewPolyline = null
        )

        val id = walkDao.insertWalk(walk)
        val result = walkDao.getAllWalks().first()

        assertThat(result).hasSize(1)
        assertThat(result[0].id).isEqualTo(id)
        assertThat(result[0].distance).isEqualTo(1.5f)
    }

    @Test
    fun insertPoints_andLoadWalkWithPoints() = runTest {
        val walkId = walkDao.insertWalk(
            WalkEntity(
                startTime = 1000L,
                endTime = 2000L,
                distance = 2.0f,
                duration = 2000L,
                pointCount = 2,
                previewPolyline = null
            )
        )

        val points = listOf(
            GpsPointEntity(walkId = walkId, sequence = 0, latitude = 60.0, longitude = 24.0, timestamp = 1000),
            GpsPointEntity(walkId = walkId, sequence = 1, latitude = 60.1, longitude = 24.1, timestamp = 2000)
        )

        walkDao.insertPoints(points)

        val result = walkDao.getWalkWithPoints(walkId)

        assertThat(result.walk.id).isEqualTo(walkId)
        assertThat(result.points).hasSize(2)
        assertThat(result.points[0].latitude).isEqualTo(60.0)
    }

    @Test
    fun getTotalDistanceSince_returnsCorrectSum() = runTest {
        walkDao.insertWalk(
            WalkEntity(
                startTime = 1000L,
                endTime = 2000L,
                distance = 1.0f,
                duration = 1000L,
                pointCount = 1
            )
        )

        walkDao.insertWalk(
            WalkEntity(
                startTime = 2000L,
                endTime = 3000L,
                distance = 2.0f,
                duration = 2000L,
                pointCount = 1
            )
        )

        val result = walkDao.getTotalDistanceSince(0L).first()

        assertThat(result).isEqualTo(3.0f)
    }
}
