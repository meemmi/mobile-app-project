package com.example.pawtracker.ui.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.v2.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class NavGraphTest {

    @get:Rule
    val composeRule = createComposeRule()

    // ---------------------------------------------------------
    // TEST-ONLY BOTTOM NAVIGATION (fake)
    // ---------------------------------------------------------
    @Composable
    fun FakeBottomNav(navController: NavHostController) {
        NavigationBar {

            NavigationBarItem(
                selected = false,
                onClick = { navController.navigate(Screen.Statistics.route) },
                icon = {},
                modifier = Modifier.semantics { contentDescription = "statistics" }
            )

            NavigationBarItem(
                selected = false,
                onClick = { navController.navigate(Screen.Tracking.route) },
                icon = {},
                modifier = Modifier.semantics { contentDescription = "map" }
            )

            NavigationBarItem(
                selected = false,
                onClick = { navController.navigate(Screen.History.route) },
                icon = {},
                modifier = Modifier.semantics { contentDescription = "history" }
            )

            NavigationBarItem(
                selected = false,
                onClick = { navController.navigate(Screen.Profile.route) },
                icon = {},
                modifier = Modifier.semantics { contentDescription = "profile" }
            )
        }
    }

    // ---------------------------------------------------------
    // TEST-ONLY NAVGRAPH WRAPPER (fake screens)
    // ---------------------------------------------------------
    @Composable
    fun TestNavGraphWrapper(
        navController: NavHostController,
        startDestination: String
    ) {
        Column {

            FakeBottomNav(navController)

            NavHost(
                navController = navController,
                startDestination = startDestination
            ) {
                composable(Screen.Main.route) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .testTag("main_screen")
                    ) {}
                }
                composable(Screen.Statistics.route) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .testTag("statistics_screen")
                    ) {}
                }
                composable(Screen.Tracking.route) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .testTag("tracking_screen")
                    ) {}
                }
                composable(Screen.History.route) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .testTag("history_screen")
                    ) {}
                }
                composable(Screen.Profile.route) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .testTag("profile_screen")
                    ) {}
                }
            }
        }
    }

    // ---------------------------------------------------------
    // SET CONTENT
    // ---------------------------------------------------------
    private fun setContent(startDestination: String) {
        composeRule.setContent {
            val navController = rememberNavController()
            TestNavGraphWrapper(
                navController = navController,
                startDestination = startDestination
            )
        }
    }

    // ---------------------------------------------------------
    // TESTS
    // ---------------------------------------------------------

    @Test
    fun startDestination_isMain() {
        setContent(Screen.Main.route)
        composeRule.onNodeWithTag("main_screen").assertIsDisplayed()
    }

    @Test
    fun startDestination_isStatistics() {
        setContent(Screen.Statistics.route)
        composeRule.onNodeWithTag("statistics_screen").assertIsDisplayed()
    }

    @Test
    fun bottomNav_navigates_to_Tracking() {
        setContent(Screen.Statistics.route)

        composeRule.onNodeWithContentDescription("map").performClick()

        composeRule.onNodeWithTag("tracking_screen").assertIsDisplayed()
    }

    @Test
    fun bottomNav_navigates_to_Profile() {
        setContent(Screen.Statistics.route)

        composeRule.onNodeWithContentDescription("profile").performClick()

        composeRule.onNodeWithTag("profile_screen").assertIsDisplayed()
    }

    @Test
    fun bottomNav_navigates_to_History() {
        setContent(Screen.Statistics.route)

        composeRule.onNodeWithContentDescription("history").performClick()

        composeRule.onNodeWithTag("history_screen").assertIsDisplayed()
    }
}
