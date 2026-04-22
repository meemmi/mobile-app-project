package com.example.pawtracker.ui.navigation
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.pawtracker.ui.history.HistoryScreen
import com.example.pawtracker.ui.main.MainScreen
import com.example.pawtracker.ui.profile.ProfileScreen
import com.example.pawtracker.ui.statistics.StatisticsScreen
import com.example.pawtracker.ui.tracking.TrackingScreen

@Composable
fun NavGraph(navController: NavHostController,
             isDarkTheme: Boolean,
             onToggleTheme: () -> Unit,
             innerPadding: PaddingValues
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Main.route
    ) {
        //Navigate to a composable
        composable(Screen.Main.route) {MainScreen( onContinueClick = {
            navController.navigate(Screen.Tracking.route)
        },
            isDarkTheme = isDarkTheme,
            onToggleTheme = onToggleTheme,
            innerPadding = innerPadding) }
        composable(Screen.Tracking.route) {TrackingScreen(innerPadding) }
        composable(Screen.History.route) {HistoryScreen(innerPadding) }
        composable(Screen.Statistics.route) {StatisticsScreen(innerPadding)}
        composable(Screen.Profile.route) { ProfileScreen(innerPadding = innerPadding) }
    }
}
