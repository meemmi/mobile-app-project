package com.example.pawtracker.ui.tracking

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TrackingScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    // BUTTON STATE TESTS
    @Test
    fun startButton_enabled_stopButton_disabled_initially() {

        val uiState = TrackingUiState(tracking = false)

        composeTestRule.setContent {
            TrackingLayout(
                uiState = uiState,
                onStart = {},
                onStop = {},
                innerPadding = PaddingValues()
            )
        }

        composeTestRule.onNodeWithTag("start_button").assertIsEnabled()
        composeTestRule.onNodeWithTag("stop_button").assertIsNotEnabled()
    }

    @Test
    fun stopButton_enabled_when_tracking() {

        val uiState = TrackingUiState(tracking = true)

        composeTestRule.setContent {
            TrackingLayout(
                uiState = uiState,
                onStart = {},
                onStop = {},
                innerPadding = PaddingValues()
            )
        }

        composeTestRule.onNodeWithTag("start_button").assertIsNotEnabled()
        composeTestRule.onNodeWithTag("stop_button").assertIsEnabled()
    }
    // BUTTON CLICK TESTS

    @Test
    fun clicking_start_calls_onStart() {

        var clicked = false

        composeTestRule.setContent {
            TrackingLayout(
                uiState = TrackingUiState(tracking = false),
                onStart = { clicked = true },
                onStop = {},
                innerPadding = PaddingValues()
            )
        }

        composeTestRule.onNodeWithTag("start_button").performClick()

        assertTrue(clicked)
    }

    @Test
    fun clicking_stop_calls_onStop() {

        var clicked = false

        composeTestRule.setContent {
            TrackingLayout(
                uiState = TrackingUiState(tracking = true),
                onStart = {},
                onStop = { clicked = true },
                innerPadding = PaddingValues()
            )
        }

        composeTestRule.onNodeWithTag("stop_button").performClick()

        assertTrue(clicked)
    }

    // STATISTICS TESTS
    @Test
    fun distance_is_displayed_correctly() {

        val uiState = TrackingUiState(distance = 1.23)

        composeTestRule.setContent {
            TrackingStatistics(uiState = uiState)
        }

        composeTestRule.onNodeWithTag("distance_text").assertTextContains("1.23 km")
    }

    @Test
    fun time_is_displayed_correctly_minutes_only() {

        val uiState = TrackingUiState(time = 60000) // 1 min

        composeTestRule.setContent {
            TrackingStatistics(uiState = uiState)
        }

        composeTestRule.onNodeWithTag("time_text").assertTextContains("1 min")
    }

    @Test
    fun time_is_displayed_correctly_hours_and_minutes() {

        val uiState = TrackingUiState(time = 3660000) // 1h 1min

        composeTestRule.setContent {
            TrackingStatistics(uiState = uiState)
        }

        composeTestRule.onNodeWithTag("time_text").assertTextContains("1 h 01 min")
    }
}
