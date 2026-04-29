package com.example.pawtracker.ui.navigation
//Define routes:
sealed class Screen(val route: String) {
    object Main : Screen("main")
    object Tracking : Screen("tracking")
    object History : Screen("history")
    object Statistics : Screen("statistics")
    object Profile : Screen("profile")

    object EditProfile : Screen("editprofile")
}


