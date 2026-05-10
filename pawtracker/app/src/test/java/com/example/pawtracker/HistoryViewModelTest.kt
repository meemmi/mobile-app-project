package com.example.pawtracker.ui.history

import com.example.pawtracker.FakeWalkRepositoryForHistory
import com.example.pawtracker.data.local.WalkEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.util.Calendar
/**
 * Unit tests for HistoryViewModel.
 * Uses a fake repository to supply predefined walks and verifies
 * that daily/weekly filtering and filter state updates work correctly.
 */

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
    fun `loads walks and filters daily correctly`() = runTest {
        val repo = FakeWalkRepositoryForHistory(
            walks = listOf(
                walkToday(),
                walkYesterday()
            )
        )

        val viewModel = HistoryViewModel(repo)

        advanceUntilIdle()

        val state = viewModel.uiState.value

        assertEquals(1, state.walks.size)
    }

    @Test
    fun `filters weekly correctly`() = runTest {
        val repo = FakeWalkRepositoryForHistory(
            walks = listOf(
                walkToday(),
                walkThisWeek(),
                walkLastWeek()
            )
        )

        val viewModel = HistoryViewModel(repo)

        advanceUntilIdle()

        viewModel.setFilter(WalkFilter.Weekly)
        advanceUntilIdle()

        val state = viewModel.uiState.value

        assertEquals(2, state.walks.size)
    }

    @Test
    fun `filter change updates ui state`() = runTest {
        val repo = FakeWalkRepositoryForHistory(
            walks = listOf(
                walkToday(),
                walkThisWeek()
            )
        )

        val viewModel = HistoryViewModel(repo)

        advanceUntilIdle()

        viewModel.setFilter(WalkFilter.Weekly)

        val state = viewModel.uiState.value

        assertEquals(WalkFilter.Weekly, state.filter)
    }

    // Helpers
    private fun walkToday(): WalkEntity {
        val now = System.currentTimeMillis()
        return WalkEntity(1, now, now, 1f, 100L, 10)
    }

    private fun walkYesterday(): WalkEntity {
        val cal = Calendar.getInstance()
        cal.add(Calendar.DAY_OF_YEAR, -1)
        val t = cal.timeInMillis
        return WalkEntity(2, t, t, 1f, 100L, 10)
    }

    private fun walkThisWeek(): WalkEntity {
        val cal = Calendar.getInstance()
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY)
        cal.add(Calendar.DAY_OF_WEEK, 1)
        val t = cal.timeInMillis
        return WalkEntity(3, t, t, 1f, 100L, 10)
    }

    private fun walkLastWeek(): WalkEntity {
        val cal = Calendar.getInstance()
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY)
        cal.add(Calendar.WEEK_OF_YEAR, -1)
        val t = cal.timeInMillis
        return WalkEntity(4, t, t, 1f, 100L, 10)
    }
}
