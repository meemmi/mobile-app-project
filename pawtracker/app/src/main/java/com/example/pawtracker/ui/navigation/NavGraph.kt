package com.example.pawtracker.ui.navigation
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
             onToggleTheme: () -> Unit) {
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
            navController = navController ) }
        composable(Screen.Tracking.route) {TrackingScreen() }
        composable(Screen.History.route) {HistoryScreen() }
        composable(Screen.Statistics.route) {StatisticsScreen()}
        composable(Screen.Profile.route) { ProfileScreen() }
    }
}
