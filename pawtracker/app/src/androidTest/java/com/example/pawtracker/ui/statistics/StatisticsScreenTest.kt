package com.example.pawtracker.ui.statistics

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.onNodeWithTag
import com.example.pawtracker.ui.theme.PawTrackerTheme
import com.example.pawtracker.ui.navigation.NavigationType
import org.junit.Rule
import org.junit.Test
/**
 * UI tests for StatisticsScreen.
 * Uses fake repositories to provide fixed statistics and profile data,
 * and verifies that the dog name is shown and the Start Walk button works.
 */
class StatisticsScreenTest {

    @get:Rule
    val composeTestRule = androidx.compose.ui.test.junit4.v2.createComposeRule()

    private fun createFakeVM(): StatisticsViewModel {
        val walkRepo = FakeWalkRepository(
            todayDistance = 2.5f,
            todayDuration = 3600000L,
            weekDistance = 10f,
            weekDuration = 7200000L
        )

        val profileRepo = FakeDogProfileRepository(fakeStatisticsProfile())

        return StatisticsViewModel(walkRepo, profileRepo)
    }

    @Test
    fun dog_name_is_displayed() {
        val vm = createFakeVM()

        composeTestRule.setContent {
            PawTrackerTheme {
                StatisticsScreen(
                    viewModel = vm,
                    innerPadding = PaddingValues(),
                    navigationType = NavigationType.BOTTOM_NAVIGATION,
                    onStartWalkClick = {}
                )
            }
        }

        composeTestRule
            .onNodeWithTag("stats_dog_name")
            .assertExists()
            .assertTextContains("Buddy")
    }

    @Test
    fun start_walk_button_triggers_callback() {
        var clicked = false
        val vm = createFakeVM()

        composeTestRule.setContent {
            PawTrackerTheme {
                StatisticsScreen(
                    viewModel = vm,
                    innerPadding = PaddingValues(),
                    navigationType = NavigationType.BOTTOM_NAVIGATION,
                    onStartWalkClick = { clicked = true }
                )
            }
        }

        composeTestRule
            .onNodeWithTag("stats_start_walk_button")
            .performClick()

        assert(clicked)
    }

}
