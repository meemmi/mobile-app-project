package com.example.pawtracker.ui.navigation
import android.app.Activity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.pawtracker.PawTrackerApplication
import com.example.pawtracker.ui.editprofile.EditProfileScreen
import com.example.pawtracker.ui.editprofile.EditProfileViewModel
import com.example.pawtracker.ui.history.HistoryScreen
import com.example.pawtracker.ui.history.HistoryViewModel
import com.example.pawtracker.ui.main.MainScreen
import com.example.pawtracker.ui.main.MainViewModel
import com.example.pawtracker.ui.profile.ProfileScreen
import com.example.pawtracker.ui.profile.ProfileViewModel
import com.example.pawtracker.ui.statistics.StatisticsScreen
import com.example.pawtracker.ui.statistics.StatisticsViewModel
import com.example.pawtracker.ui.tracking.TrackingScreen
import com.example.pawtracker.ui.tracking.TrackingViewModel
import com.example.pawtracker.utils.ViewModelFactory

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun NavGraph(
    navController: NavHostController,
    isDarkTheme: Boolean,
    onToggleTheme: () -> Unit,
    innerPadding: PaddingValues,
    viewModel: MainViewModel,
    app: PawTrackerApplication,
    navigationType: NavigationType
) {
    val hasCompletedOnboarding = viewModel.hasCompletedOnboarding

    if (hasCompletedOnboarding == null) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
        return
    }

    val startDest = if (hasCompletedOnboarding) Screen.Statistics.route else Screen.Main.route

    NavHost(
        navController = navController,
        startDestination = startDest
    ) {

        // 1. Main screen / Onboarding screen
        composable(Screen.Main.route) {
            MainScreen(
                viewModel = viewModel,
                onContinueClick = {
                    viewModel.completeOnboarding() // Save onboarding status
                    navController.navigate(Screen.Statistics.route) { // Navgates to statisticsScreen
                        popUpTo(Screen.Main.route) { inclusive = true }
                    }
                },
                innerPadding = innerPadding,
                navigationType = navigationType
            )
        }

        // 2. Statistics screen
        composable(Screen.Statistics.route) {
            val statsVm: StatisticsViewModel = viewModel(
                factory = ViewModelFactory {
                    StatisticsViewModel(app.walkRepository, app.dogProfileRepository)
                }
            )
            StatisticsScreen(
                innerPadding = innerPadding,
                viewModel = statsVm,
                onStartWalkClick = {navController.navigate(Screen.Tracking.route) {
                    popUpTo(navController.graph.findStartDestination().id) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                } },
                navigationType = navigationType
            )
        }

        // 3. Tracking screen
        composable(Screen.Tracking.route) {
            val trackingVm: TrackingViewModel = viewModel(
                factory = ViewModelFactory {
                    TrackingViewModel(app.gpsRepository, app.walkRepository)
                }
            )
            TrackingScreen(innerPadding = innerPadding, viewModel = trackingVm, navigationType = navigationType)
        }

        // 4. History screen
        composable(Screen.History.route) {
            val historyVm: HistoryViewModel = viewModel(
                factory = ViewModelFactory {
                    HistoryViewModel(app.walkRepository)
                }
            )
            HistoryScreen(innerPadding = innerPadding, viewModel = historyVm, navigationType = navigationType)
        }

        // 5. Profile screen
        composable(Screen.Profile.route) {
            val profileVm: ProfileViewModel = viewModel(
                factory = ViewModelFactory {
                    ProfileViewModel(app.dogProfileRepository)
                }
            )
            ProfileScreen(
                innerPadding = innerPadding,
                viewModel = profileVm,
                onNavigateToEdit = {
                    navController.navigate(Screen.EditProfile.route) },
                navigationType = navigationType,
                isDarkTheme = isDarkTheme,
                onToggleTheme = onToggleTheme
            )
        }

        // 6. EditProfileScreen
        composable(Screen.EditProfile.route) {
            val editVm: EditProfileViewModel = viewModel(
                factory = ViewModelFactory {
                    EditProfileViewModel(app.dogProfileRepository)
                }
            )
            EditProfileScreen(
                viewModel = editVm,
                onNavigateBack = { navController.popBackStack() },
                innerPadding = innerPadding,
                navigationType = navigationType
            )
        }
    }
}


