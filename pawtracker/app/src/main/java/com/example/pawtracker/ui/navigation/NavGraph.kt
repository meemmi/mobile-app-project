package com.example.pawtracker.ui.navigation
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.pawtracker.data.repository.DogProfileRepository
import com.example.pawtracker.data.repository.GPSRepository
import com.example.pawtracker.data.repository.WalkRepository
import com.example.pawtracker.ui.history.HistoryScreen
import com.example.pawtracker.ui.history.HistoryViewModel
import com.example.pawtracker.ui.history.HistoryViewModelFactory
import com.example.pawtracker.ui.main.MainScreen
import com.example.pawtracker.ui.profile.ProfileScreen
import com.example.pawtracker.ui.profile.ProfileViewModel
import com.example.pawtracker.ui.profile.ProfileViewModelFactory
import com.example.pawtracker.ui.statistics.StatisticsScreen
import com.example.pawtracker.ui.statistics.StatisticsViewModel
import com.example.pawtracker.ui.statistics.StatisticsViewModelFactory
import com.example.pawtracker.ui.tracking.TrackingScreen
import com.example.pawtracker.ui.tracking.TrackingViewModel
import com.example.pawtracker.ui.tracking.TrackingViewModelFactory

@Composable
fun NavGraph(navController: NavHostController,
             isDarkTheme: Boolean,
             onToggleTheme: () -> Unit,
             innerPadding: PaddingValues,
             gpsRepository: GPSRepository,
             walkRepository: WalkRepository,
             dogProfileRepository: DogProfileRepository
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Main.route
    ) {
        composable(Screen.Main.route) {
            MainScreen(
                onContinueClick = { navController.navigate(Screen.Tracking.route) },
                isDarkTheme = isDarkTheme,
                onToggleTheme = onToggleTheme,   // ✔ OK
                innerPadding = innerPadding
            )
        }
        //Navigate to a composable
        composable(Screen.Main.route) {MainScreen( onContinueClick = {
            navController.navigate(Screen.Tracking.route)
        },
            isDarkTheme = isDarkTheme,
            onToggleTheme = onToggleTheme,
            innerPadding = innerPadding) }
        composable(Screen.Tracking.route) {
            val viewModel: TrackingViewModel = viewModel(
                factory = TrackingViewModelFactory(gpsRepository, walkRepository)
            )
            TrackingScreen(viewModel, innerPadding)
        }

        composable(Screen.History.route) {
            val viewModel: HistoryViewModel = viewModel(
                factory = HistoryViewModelFactory(walkRepository)
            )
            HistoryScreen(viewModel, innerPadding)
        }

        composable(Screen.Statistics.route) {
            val viewModel: StatisticsViewModel = viewModel(
                factory = StatisticsViewModelFactory(walkRepository, dogProfileRepository)
            )
            StatisticsScreen(viewModel, innerPadding)
        }

        composable(Screen.Profile.route) {
            val viewModel: ProfileViewModel = viewModel(
                factory = ProfileViewModelFactory(dogProfileRepository)
            )
            ProfileScreen(viewModel, innerPadding)
        }
    }

}
