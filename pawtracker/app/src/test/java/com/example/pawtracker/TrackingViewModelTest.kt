package com.example.pawtracker

import com.example.pawtracker.model.LocationPoint
import com.example.pawtracker.ui.tracking.TrackingViewModel
import com.example.pawtracker.data.repository.GPSRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.setMain
import kotlinx.coroutines.test.resetMain
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class TrackingViewModelTest {

    private val dispatcher = StandardTestDispatcher()

    private lateinit var fakeGps: FakeGPSRepository
    private lateinit var fakeWalkRepo: FakeWalkRepositoryForTracking
    private lateinit var viewModel: TrackingViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher)

        fakeGps = FakeGPSRepository()
        fakeWalkRepo = FakeWalkRepositoryForTracking()

        viewModel = TrackingViewModel(
            gpsRepository = fakeGps,
            walkRepository = fakeWalkRepo
        )

        viewModel.setUseMockLocation(false)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    // ---------------------------------------------------------
    // START TRACKING
    // ---------------------------------------------------------
    @Test
    fun `startTracking sets tracking true and resets state`() = runTest {

        viewModel.startTracking()
        advanceUntilIdle()

        val state = viewModel.uiState.value

        assertTrue(state.tracking)
        assertEquals(0.0, state.distance, 0.0)
        assertEquals(0L, state.time)
        assertTrue(state.points.isEmpty())
    }

    // ---------------------------------------------------------
    // GPS UPDATES
    // ---------------------------------------------------------
    @Test
    fun `GPS emits points and updates state`() = runTest {

        viewModel.startTracking()
        advanceUntilIdle()

        val p1 = LocationPoint(60.0, 24.0, time = 1000L)
        val p2 = LocationPoint(60.00005, 24.00005, time = 2000L)

        fakeGps.emit(p1)
        runCurrent()

        fakeGps.emit(p2)
        runCurrent()

        val state = viewModel.uiState.value

        assertEquals(2, state.points.size)
        assertEquals(p2, state.currentLocation)
        assertTrue(state.distance > 0.0)
    }

    // ---------------------------------------------------------
    // STOP TRACKING
    // ---------------------------------------------------------
    @Test
    fun `stopTracking saves walk and resets UI`() = runTest {

        viewModel.startTracking()
        advanceUntilIdle()

        val p = LocationPoint(60.0, 24.0, time = 1000L)
        fakeGps.emit(p)
        runCurrent()

        viewModel.stopTracking()
        advanceUntilIdle()

        val state = viewModel.uiState.value

        // UI reset
        assertFalse(state.tracking)
        assertEquals(0.0, state.distance, 0.0)
        assertEquals(0, state.points.size)

        // Saved
        assertNotNull(fakeWalkRepo.savedWalk)
        assertEquals(1, fakeWalkRepo.savedPoints.size)
    }

    // ---------------------------------------------------------
    // LOAD LAST LOCATION
    // ---------------------------------------------------------
    @Test
    fun `loadLastLocation updates currentLocation`() = runTest {

        val last = LocationPoint(60.5, 25.0, time = 123L)
        fakeGps.lastLocation = last

        viewModel.loadLastLocation()
        advanceUntilIdle()

        val state = viewModel.uiState.value

        assertEquals(last, state.currentLocation)
    }
}