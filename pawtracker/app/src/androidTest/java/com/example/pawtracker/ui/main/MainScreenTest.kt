package com.example.pawtracker.ui.main

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.unit.dp
import org.junit.Rule
import org.junit.Test

class MainScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun continueButton_isDisplayed() {
        composeTestRule.setContent {
            MainScreen(
                onContinueClick = {},
                isDarkTheme = false,
                onToggleTheme = {},
                innerPadding = PaddingValues(0.dp)
            )
        }

        composeTestRule
            .onNodeWithText("Continue")
            .assertIsDisplayed()
    }
}