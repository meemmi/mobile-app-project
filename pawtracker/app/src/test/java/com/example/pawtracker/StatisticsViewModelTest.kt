package com.example.pawtracker

import app.cash.turbine.test
import com.example.pawtracker.ui.statistics.StatisticsViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import org.junit.Assert.assertEquals


@OptIn(ExperimentalCoroutinesApi::class)
class StatisticsViewModelTest {

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
    fun `combines repository data into statistics ui state`() = runTest {

        val viewModel = StatisticsViewModel(
            FakeWalkRepository(),
            FakeDogProfileRepository()
        )

        viewModel.uiState.test {

            awaitItem()
            val state = awaitItem()

            advanceUntilIdle()

            assertEquals(2f, state.todayDistance)
            assertEquals(100L, state.todayDuration)
            assertEquals(10f, state.weekDistance)
            assertEquals(500L, state.weekDuration)

            assertEquals(5f, state.goalDistance)
            assertEquals(30L, state.goalDuration)

            assertEquals("Nelli", state.dogName)
            assertEquals("testUri", state.imageUri)

            cancelAndIgnoreRemainingEvents()
        }
    }
}
