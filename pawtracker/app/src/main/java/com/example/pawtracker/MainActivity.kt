package com.example.pawtracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.pawtracker.data.repository.GPSRepository
import com.example.pawtracker.data.repository.DogProfileRepository
import com.example.pawtracker.ui.theme.PawTrackerTheme
import com.example.pawtracker.ui.tracking.TrackingScreen
import com.example.pawtracker.ui.tracking.TrackingViewModel
import com.example.pawtracker.data.repository.WalkRepository
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Text
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.room.Room
import kotlin.getValue
import com.example.pawtracker.data.local.AppDatabase
import com.example.pawtracker.data.local.MIGRATION_1_2
import com.example.pawtracker.ui.history.HistoryScreen
import com.example.pawtracker.ui.history.HistoryViewModel
import com.example.pawtracker.ui.profile.ProfileScreen
import com.example.pawtracker.ui.statistics.StatisticsScreen
import com.example.pawtracker.ui.statistics.StatisticsViewModel
import com.example.pawtracker.ui.profile.ProfileViewModel



class MainActivity : ComponentActivity() {

    // 1. Create repository + ViewModel
    private val database by lazy {
        Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "walk_db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    private val gpsRepository by lazy { GPSRepository(this) }

    private val walkRepository by lazy {WalkRepository(database.walkDao())}

    private val dogProfileRepository by lazy { DogProfileRepository(database.dogProfileDao())}

    private val historyViewModel by lazy { HistoryViewModel(walkRepository) }

    private val trackingViewModel by lazy { TrackingViewModel(gpsRepository, walkRepository) }

    private val profileViewModel by lazy { ProfileViewModel(dogProfileRepository) }

    private val statisticsViewModel by lazy { StatisticsViewModel(walkRepository, dogProfileRepository) }

    // 2. Permission launcher must be inside class
    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
            // You can show a message if needed
        }

    // 3. Permission request function
    private fun requestLocationPermission() {
        requestPermissionLauncher.launch(android.Manifest.permission.ACCESS_FINE_LOCATION)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 4. Ask for permission
        requestLocationPermission()

        // 5. Edge to edge
        enableEdgeToEdge()

        // 6. Set content
        setContent {
           PawTrackerTheme {
           // Shows TrackingScreen with your unified ViewModel

             //TrackingScreen(viewModel = trackingViewModel)
             // HistoryScreen(viewModel = historyViewModel)
              StatisticsScreen(viewModel = statisticsViewModel)
               //ProfileScreen(viewModel = profileViewModel)

           }
        }
    }
}

// 7. Preview function must be outside MainActivity
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    PawTrackerTheme {
        // You can preview UI here
    }
}