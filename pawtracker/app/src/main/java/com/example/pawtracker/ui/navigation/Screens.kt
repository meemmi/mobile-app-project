package com.example.pawtracker.ui.navigation
/**
 * Defines all navigation routes used in the app.
 * Each screen is represented as an object with a unique route string.
 */

sealed class Screen(val route: String) {
    object Main : Screen("main")
    object Tracking : Screen("tracking")
    object History : Screen("history")
    object Statistics : Screen("statistics")
    object Profile : Screen("profile")

    object EditProfile : Screen("editprofile")
}


