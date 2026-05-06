package com.example.pawtracker.ui.statistics

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.onNodeWithTag
import com.example.pawtracker.ui.theme.PawTrackerTheme
import androidx.compose.ui.test.junit4.createComposeRule
import com.example.pawtracker.ui.navigation.NavigationType
import org.junit.Rule
import org.junit.Test

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

   /* @Test
    fun progress_percentage_is_displayed() {
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

        // Force ViewModel coroutines to run and emit values
        composeTestRule.mainClock.advanceTimeBy(1_000)

        // Wait until the UI updates from 0% to real percentage
        composeTestRule.waitUntil(timeoutMillis = 5_000) {
            composeTestRule
                .onNodeWithTag("stats_progress_percent")
                .fetchSemanticsNode()
                .config[androidx.compose.ui.semantics.SemanticsProperties.Text]
                .firstOrNull()
                ?.text
                ?.contains("%") == true
        }

        // FakeWalkRepository: 2.5 km today, goal 3.5 km
        val expectedPercent = ((2.5f / 3.5f) * 100).toInt().toString()

        composeTestRule
            .onNodeWithTag("stats_progress_percent")
            .assertExists()
            .assertTextContains(expectedPercent)
    }*/

}
