package com.example.pawtracker

import android.content.pm.PackageManager
import android.os.Build
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
import com.example.pawtracker.data.worker.ReminderScheduler
import com.example.pawtracker.ui.main.MainViewModel
import com.example.pawtracker.utils.NotificationHelper
import com.example.pawtracker.utils.ViewModelFactory


class MainActivity : ComponentActivity() {

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
        }

    private fun requestLocationPermission() {
        requestPermissionLauncher.launch(android.Manifest.permission.ACCESS_FINE_LOCATION)
    }

    // NOTIFICATION PERMISSION (Android 13+)
    private val requestNotificationPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
            // Optional: show toast if denied
        }
    private fun requestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (checkSelfPermission(android.Manifest.permission.POST_NOTIFICATIONS)
                != PackageManager.PERMISSION_GRANTED
            ) {
                requestNotificationPermissionLauncher.launch(
                    android.Manifest.permission.POST_NOTIFICATIONS
                )
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val app = application as PawTrackerApplication

// 1️⃣ Request permissions
        requestLocationPermission()
        requestNotificationPermission()

        // 2️⃣ Create notification channel
        NotificationHelper.createChannel(this)

        // 3️⃣ Schedule daily reminder
        ReminderScheduler.scheduleDailyReminder(this)

        // 4️⃣ UI setup
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