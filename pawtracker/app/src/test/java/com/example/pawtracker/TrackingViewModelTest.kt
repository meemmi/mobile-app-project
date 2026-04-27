package com.example.pawtracker
import com.example.pawtracker.ui.tracking.TrackingViewModel
import app.cash.turbine.test
import com.example.pawtracker.model.LocationPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.After
import org.junit.Test
import org.junit.Assert.*
import org.junit.Before
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runCurrent

@OptIn(ExperimentalCoroutinesApi::class)
class TrackingViewModelTest {

    private val dispatcher = StandardTestDispatcher()

    private lateinit var fakeGps: FakeGPSRepository
    private lateinit var fakeWalkRepo: FakeWalkRepository
    private lateinit var viewModel: TrackingViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher)

        fakeGps = FakeGPSRepository()
        fakeWalkRepo = FakeWalkRepository()

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

    @Test
    fun `startTracking sets tracking true and resets state`() = runTest {

        viewModel.startTracking()
        advanceUntilIdle()

        viewModel.uiState.test {
            val state = awaitItem()

            assertTrue(state.tracking)
            assertEquals(0f, state.distance)
            assertEquals(0L, state.time)
            assertTrue(state.points.isEmpty())
        }
    }

    @Test
    fun `GPS emits points and ViewModel updates distance and points`() = runTest {

        viewModel.setUseMockLocation(false) // use fake GPS
        viewModel.startTracking()
        advanceUntilIdle()

        // Emit first point
        val p1 = LocationPoint(60.0, 24.0, time = 0L)
        fakeGps.emit(p1)

        // Emit second point
        val p2 = LocationPoint(60.0005, 24.0005, time = 1000L)
        fakeGps.emit(p2)

        advanceUntilIdle()

        val state = viewModel.uiState.value

        assertEquals(2, state.points.size)
        assertEquals(p2, state.currentLocation)
        assertTrue(state.distance > 0f)
    }

    @Test
    fun `stopTracking saves walk and resets UI`() = runTest {

        viewModel.setUseMockLocation(false)
        viewModel.startTracking()

        val p = LocationPoint(60.0, 24.0, time = 0L)
        fakeGps.emit(p)

        // 🔥 ADD THIS LINE
        runCurrent()  // <-- forces state update immediately

        viewModel.stopTracking()

        advanceUntilIdle()

        val state = viewModel.uiState.value

        // UI reset
        assertFalse(state.tracking)
        assertEquals(0f, state.distance)
        assertEquals(0, state.points.size)

        // Walk saved
        assertNotNull(fakeWalkRepo.savedWalk)
        assertEquals(1, fakeWalkRepo.savedPoints.size)
    }
    @Test
    fun `loadLastLocation updates currentLocation`() = runTest {

        viewModel.setUseMockLocation(false)

        val last = LocationPoint(60.5, 25.0, time = 123L)
        fakeGps.lastLocation = last

        viewModel.loadLastLocation()
        advanceUntilIdle()

        val state = viewModel.uiState.value

        assertEquals(last, state.currentLocation)
    }
}
