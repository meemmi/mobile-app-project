package com.example.pawtracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.pawtracker.data.repository.GPSRepository
import com.example.pawtracker.data.repository.DogProfileRepositoryImpl
import com.example.pawtracker.ui.theme.PawTrackerTheme
import com.example.pawtracker.ui.tracking.TrackingScreen
import com.example.pawtracker.ui.tracking.TrackingViewModel
import com.example.pawtracker.data.repository.WalkRepository
import com.example.pawtracker.ui.editprofile.EditProfileViewModel
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import kotlin.getValue
import com.example.pawtracker.data.local.AppDatabase
import com.example.pawtracker.data.repository.WalkRepositoryImpl
import com.example.pawtracker.ui.history.HistoryViewModel
import com.example.pawtracker.ui.navigation.NavGraph
import com.example.pawtracker.ui.profile.ProfileScreen
import com.example.pawtracker.ui.statistics.StatisticsViewModel
import com.example.pawtracker.ui.profile.ProfileViewModel
import com.example.pawtracker.ui.statistics.StatisticsScreen
import androidx.compose.runtime.*
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.saveable.rememberSaveable
import com.example.pawtracker.ui.components.NavBar
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.ViewModelProvider
import com.example.pawtracker.ui.main.MainViewModel
import com.example.pawtracker.utils.ViewModelFactory


class MainActivity : ComponentActivity() {

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
        }

    private fun requestLocationPermission() {
        requestPermissionLauncher.launch(android.Manifest.permission.ACCESS_FINE_LOCATION)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val app = application as PawTrackerApplication


        // Ask for permission
        requestLocationPermission()
        enableEdgeToEdge()

        setContent {

            val mainViewModel: MainViewModel = viewModel(
                factory = ViewModelFactory { MainViewModel(app.preferenceRepository) }
            )

            val systemDark = isSystemInDarkTheme()
            var isDarkTheme by rememberSaveable { mutableStateOf(systemDark) }

            PawTrackerTheme(darkTheme = isDarkTheme) {
                val navController = rememberNavController()

                Scaffold(
                    bottomBar = { NavBar(navController, has_completed_onboarding = mainViewModel.hasCompletedOnboarding ?: false) }
                ) { innerPadding ->
                    NavGraph(
                        navController = navController,
                        isDarkTheme = isDarkTheme,
                        onToggleTheme = {isDarkTheme = !isDarkTheme },
                        innerPadding = innerPadding,
                        viewModel = mainViewModel,
                        app = app
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    PawTrackerTheme {

    }
}