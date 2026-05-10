package com.example.pawtracker.ui.main
/**
 * UI state for the onboarding screen.
 * Shows the welcome text and short description when the app is opened for the first time.
 */

data class MainUiState(
    val welcomeText: String = "Welcome to PawTracker",
    val description: String = "Get to know how your dog moves and stays active"
)
