package com.example.pawtracker.ui.navigation
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.pawtracker.data.repository.DogProfileRepositoryImpl
import com.example.pawtracker.ui.editprofile.EditProfileScreen
import com.example.pawtracker.ui.editprofile.EditProfileViewModel
import com.example.pawtracker.ui.editprofile.EditProfileViewModelFactory
import com.example.pawtracker.ui.history.HistoryScreen
import com.example.pawtracker.ui.main.MainScreen
import com.example.pawtracker.ui.main.MainViewModel
import com.example.pawtracker.ui.profile.ProfileScreen
import com.example.pawtracker.ui.profile.ProfileViewModel
import com.example.pawtracker.ui.profile.ProfileViewModelFactory
import com.example.pawtracker.ui.statistics.StatisticsScreen
import com.example.pawtracker.ui.statistics.StatisticsViewModel
import com.example.pawtracker.ui.tracking.TrackingScreen

@Composable
fun NavGraph(navController: NavHostController,
             isDarkTheme: Boolean,
             onToggleTheme: () -> Unit,
             innerPadding: PaddingValues,
             viewModel: MainViewModel,
             statisticsViewModel: StatisticsViewModel,
             dogProfileRepository: DogProfileRepositoryImpl
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
        composable(Screen.Main.route) {MainScreen( viewModel = viewModel, onContinueClick = {
            viewModel.completeOnboarding() // Save onboarding status
            navController.navigate(Screen.Statistics.route) { // Navgates to statisticsScreen
                popUpTo(Screen.Main.route) { inclusive = true }
            }
        },
            isDarkTheme = isDarkTheme,
            onToggleTheme = onToggleTheme,
            innerPadding = innerPadding) }
        composable(Screen.Statistics.route) {StatisticsScreen(innerPadding = innerPadding, viewModel = statisticsViewModel)}
        composable(Screen.Tracking.route) {TrackingScreen(innerPadding) }
        composable(Screen.History.route) {HistoryScreen(innerPadding) }

        composable(Screen.Profile.route) {
            val profileViewModel: ProfileViewModel = viewModel(
                factory = ProfileViewModelFactory(dogProfileRepository)
            )
            ProfileScreen(
                innerPadding = innerPadding,
                viewModel = profileViewModel,
                onNavigateToEdit = { navController.navigate(Screen.EditProfile.route) }
            )
        }

        // MUOKKAUSNÄKYMÄ
        composable(Screen.EditProfile.route) {
            val editViewModel: EditProfileViewModel = viewModel(
                factory = EditProfileViewModelFactory(dogProfileRepository)
            )
            EditProfileScreen(
                viewModel = editViewModel,
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}

