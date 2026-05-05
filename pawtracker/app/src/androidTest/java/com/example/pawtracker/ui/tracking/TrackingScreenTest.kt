package com.example.pawtracker.ui.tracking

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.v2.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TrackingScreenTest {

    @get:Rule
    val composeRule = createComposeRule()

    // ---------------------------------------------------------
    // BUTTON STATE TESTS
    // ---------------------------------------------------------

    @Test
    fun startButton_enabled_stopButton_disabled_initially() {

        composeRule.setContent {
            ControlButtons(
                tracking = false,
                onStart = {},
                onStop = {}
            )
        }

        composeRule.onNodeWithTag("tracking_start_button").assertIsEnabled()
        composeRule.onNodeWithTag("tracking_stop_button").assertIsNotEnabled()
    }

    @Test
    fun stopButton_enabled_when_tracking() {

        composeRule.setContent {
            ControlButtons(
                tracking = true,
                onStart = {},
                onStop = {}
            )
        }

        composeRule.onNodeWithTag("tracking_start_button").assertIsNotEnabled()
        composeRule.onNodeWithTag("tracking_stop_button").assertIsEnabled()
    }

    // ---------------------------------------------------------
    // BUTTON CLICK TESTS
    // ---------------------------------------------------------

    @Test
    fun clicking_start_calls_onStart() {

        var clicked = false

        composeRule.setContent {
            ControlButtons(
                tracking = false,
                onStart = { clicked = true },
                onStop = {}
            )
        }

        composeRule.onNodeWithTag("tracking_start_button").performClick()

        assertTrue(clicked)
    }

    @Test
    fun clicking_stop_calls_onStop() {

        var clicked = false

        composeRule.setContent {
            ControlButtons(
                tracking = true,
                onStart = {},
                onStop = { clicked = true }
            )
        }

        composeRule.onNodeWithTag("tracking_stop_button").performClick()

        assertTrue(clicked)
    }

    // ---------------------------------------------------------
    // STATISTICS TESTS
    // ---------------------------------------------------------

    @Test
    fun distance_is_displayed_correctly() {

        val uiState = TrackingUiState(distance = 1.23)

        composeRule.setContent {
            TrackingStatistics(uiState = uiState)
        }

        composeRule.onNodeWithTag("tracking_distance").assertTextContains("1.23 km")
    }

    @Test
    fun time_is_displayed_correctly_minutes_only() {

        val uiState = TrackingUiState(time = 60000) // 1 min

        composeRule.setContent {
            TrackingStatistics(uiState = uiState)
        }

        composeRule.onNodeWithTag("tracking_time").assertTextContains("1 min")
    }

    @Test
    fun time_is_displayed_correctly_hours_and_minutes() {

        val uiState = TrackingUiState(time = 3660000) // 1h 1min

        composeRule.setContent {
            TrackingStatistics(uiState = uiState)
        }

        composeRule.onNodeWithTag("tracking_time").assertTextContains("1 h 01 min")
    }
}
