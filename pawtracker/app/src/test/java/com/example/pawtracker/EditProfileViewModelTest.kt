package com.example.pawtracker

import com.example.pawtracker.data.local.DogProfileEntity
import com.example.pawtracker.data.repository.DogProfileRepository
import com.example.pawtracker.data.repository.DogProfileRepositoryImpl
import com.example.pawtracker.ui.profile.ProfileViewModel
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
/*
@OptIn(ExperimentalCoroutinesApi::class)
class EditProfileViewModelTest {

    private val dispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    class FakeDogProfileRepository : DogProfileRepository {

        var savedProfile: DogProfileEntity? = null

        override fun getProfile(): Flow<DogProfileEntity?> {
            return flowOf(savedProfile)
        }

        override suspend fun saveProfile(profile: DogProfileEntity) {
            savedProfile = profile
        }
    }

    @Test
    fun `onNameChange updates ui state`() = runTest {

        val repo = FakeDogProfileRepository()
        val vm = ProfileViewModel(repo)

        vm.onNameChange("Rex")

        assertEquals("Rex", vm.uiState.value.name)
    }

    @Test
    fun `onBreedChange updates ui state`() = runTest {

        val repo = FakeDogProfileRepository()
        val vm = ProfileViewModel(repo)

        vm.onBreedChange("Labrador")

        assertEquals("Labrador", vm.uiState.value.breed)
    }

    @Test
    fun `save stores correct entity`() = runTest {

        val repo = FakeDogProfileRepository()
        val vm = ProfileViewModel(repo)

        vm.onNameChange("Nelli")
        vm.onBreedChange("Mix")
        vm.onDailyDistanceChange("5.5")
        vm.onDailyDurationChange("60")
        vm.onWeeklyDistanceChange("20")
        vm.onWeeklyDurationChange("120")

        vm.save()

        advanceUntilIdle()

        val saved = repo.saved!!

        assertEquals("Nelli", saved.name)
        assertEquals("Mix", saved.breed)
        assertEquals(5.5f, saved.dailyDistanceGoal)
        assertEquals(60L, saved.dailyDurationGoal)
        assertEquals(20f, saved.weeklyDistanceGoal)
        assertEquals(120L, saved.weeklyDurationGoal)
    }

    @Test
    fun `initial profile loads into ui state`() = runTest {

        val repo = FakeDogProfileRepository().apply {
            saved = DogProfileEntity(
                id = 1,
                imageUri = "img",
                name = "Nelli",
                breed = "Husky",
                dailyDistanceGoal = 3f,
                dailyDurationGoal = 30L,
                weeklyDistanceGoal = 10f,
                weeklyDurationGoal = 120L
            )
        }

        val vm = ProfileViewModel(repo)

        advanceUntilIdle()

        val state = vm.uiState.value

        assertEquals("Nelli", state.name)
        assertEquals("Husky", state.breed)
        assertEquals("3", state.dailyDistanceGoal)
        assertEquals("30", state.dailyDurationGoal)
        assertEquals("10", state.weeklyDistanceGoal)
        assertEquals("120", state.weeklyDurationGoal)
    }
}
*/