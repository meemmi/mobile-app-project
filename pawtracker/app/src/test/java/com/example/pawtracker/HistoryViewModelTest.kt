package com.example.pawtracker.ui.history

import app.cash.turbine.test
import com.example.pawtracker.FakeWalkRepository
import com.example.pawtracker.data.local.WalkEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.util.Calendar

@OptIn(ExperimentalCoroutinesApi::class)
class HistoryViewModelTest {

    private val dispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }


    @Test
    fun `loads walks and filter daily correctly`() = runTest {

        val repo = FakeWalkRepository(
            walks = listOf(
                walkToday(),
                walkYesterday()
            )
        )

        val viewModel = HistoryViewModel(repo)

        advanceUntilIdle()

        viewModel.uiState.test {
            val state = awaitItem()

            assertEquals(1, state.walks.size)
        }
    }


    @Test
    fun `filters weekly correctly`() = runTest {

        val repo = FakeWalkRepository(
            walks = listOf(
                walkToday(),
                walkThisWeek(),
                walkLastWeek()
            )
        )

        val viewModel = HistoryViewModel(repo)

        advanceUntilIdle()

        viewModel.setFilter(WalkFilter.Weekly)

        viewModel.uiState.test {
            val state = awaitItem()

            assertEquals(2, state.walks.size)
        }
    }


    @Test
    fun `filter change updates ui state`() = runTest {

        val repo = FakeWalkRepository(
            walks = listOf(walkToday(), walkThisWeek())
        )

        val viewModel = HistoryViewModel(repo)

        advanceUntilIdle()

        viewModel.setFilter(WalkFilter.Weekly)

        val state = viewModel.uiState.value

        assertEquals(WalkFilter.Weekly, state.filter)
    }


    // Helper data
    private fun walkToday(): WalkEntity {
        return WalkEntity(
            id = 1,
            startTime = now(),
            endTime = now(),
            distance = 1f,
            duration = 100L,
            pointCount = 10
        )
    }

    private fun walkYesterday(): WalkEntity {
        val cal = Calendar.getInstance()
        cal.add(Calendar.DAY_OF_YEAR, -1)

        return WalkEntity(
            id = 2,
            startTime = cal.timeInMillis,
            endTime = cal.timeInMillis,
            distance = 1f,
            duration = 100L,
            pointCount = 10
        )
    }

    private fun walkThisWeek(): WalkEntity {
        val cal = Calendar.getInstance()
        cal.add(Calendar.DAY_OF_YEAR, -2)

        return WalkEntity(
            id = 3,
            startTime = cal.timeInMillis,
            endTime = cal.timeInMillis,
            distance = 1f,
            duration = 100L,
            pointCount = 10
        )
    }

    private fun walkLastWeek(): WalkEntity {
        val cal = Calendar.getInstance()
        cal.add(Calendar.WEEK_OF_YEAR, -1)

        return WalkEntity(
            id = 4,
            startTime = cal.timeInMillis,
            endTime = cal.timeInMillis,
            distance = 1f,
            duration = 100L,
            pointCount = 10
        )
    }

    private fun now(): Long = System.currentTimeMillis()
}