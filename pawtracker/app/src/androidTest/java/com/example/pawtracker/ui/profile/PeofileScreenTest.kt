package com.example.pawtracker.ui.profile

import com.example.pawtracker.ui.navigation.NavigationType
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import com.example.pawtracker.data.local.DogProfileEntity
import com.example.pawtracker.ui.theme.PawTrackerTheme
import org.junit.Rule
import org.junit.Test

class ProfileScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private fun createFakeVM(profile: DogProfileEntity): ProfileViewModel {
        val fakeRepo = FakeDogProfileRepository(profile)
        return ProfileViewModel(fakeRepo)
    }

    private fun fakeProfile() = DogProfileEntity(
        id = 0,
        imageUri = "",
        name = "Buddy",
        breed = "Golden Retriever",
        age = "5",
        height = "60",
        weight = "30",
        dailyDurationGoal = 45L,
        dailyDistanceGoal = 3.5f,
        weeklyDurationGoal = 300L,
        weeklyDistanceGoal = 20f
    )

    @Test
    fun profile_name_is_displayed() {
        val vm = createFakeVM(fakeProfile())

        composeTestRule.setContent {
            PawTrackerTheme(darkTheme = false) {
                ProfileScreen(
                    innerPadding = PaddingValues(),
                    viewModel = vm,
                    onNavigateToEdit = {},
                    navigationType = NavigationType.BOTTOM_NAVIGATION,
                    isDarkTheme = false,
                    onToggleTheme = {}
                )
            }
        }
        composeTestRule.waitForIdle()
        composeTestRule
            .onNodeWithTag("profile_name")
            .assertExists()
            .assertTextContains("Buddy")
    }

    @Test
    fun profile_name_is_displayed_in_dark_mode() {
        val vm = createFakeVM(fakeProfile())

        composeTestRule.setContent {
            PawTrackerTheme(darkTheme = true) {
                ProfileScreen(
                    innerPadding = PaddingValues(),
                    viewModel = vm,
                    onNavigateToEdit = {},
                    navigationType = NavigationType.BOTTOM_NAVIGATION,
                    isDarkTheme = true,
                    onToggleTheme = {}
                )
            }
        }

        composeTestRule
            .onNodeWithTag("profile_name")
            .assertExists()
            .assertTextContains("Buddy")
    }
}