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
import androidx.activity.enableEdgeToEdge
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


class MainActivity : ComponentActivity() {
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

    private val walkRepository: WalkRepository by lazy { WalkRepositoryImpl(database.walkDao()) }

    private val dogProfileRepository by lazy { DogProfileRepositoryImpl(database.dogProfileDao())}

    private val historyViewModel by lazy { HistoryViewModel(walkRepository) }

    private val trackingViewModel by lazy { TrackingViewModel(gpsRepository, walkRepository) }

    private val profileViewModel by lazy { ProfileViewModel(dogProfileRepository) }

    private val statisticsViewModel by lazy { StatisticsViewModel(walkRepository, dogProfileRepository) }

    // Permission launcher must be inside class
    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
            // You can show a message if needed
        }
    private fun requestLocationPermission() {
        requestPermissionLauncher.launch(android.Manifest.permission.ACCESS_FINE_LOCATION)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Ask for permission
        requestLocationPermission()
        enableEdgeToEdge()
        setContent {
           PawTrackerTheme {
               val navController = rememberNavController()
               NavGraph(navController = navController)
           // Shows TrackingScreen with your unified ViewModel

            // TrackingScreen(viewModel = trackingViewModel)
             // HistoryScreen(viewModel = historyViewModel)
             // StatisticsScreen(viewModel = statisticsViewModel)
             //  ProfileScreen(viewModel = profileViewModel)

           }
        }
    }
}

@Preview(
    uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES,
    name = "Dark"
)
@Preview(
    uiMode = android.content.res.Configuration.UI_MODE_NIGHT_NO,
    name = "Light"
)
@Composable
fun PawTrackerPreview() {
    PawTrackerTheme {
        val navController = rememberNavController()
        NavGraph(navController = navController)
    }
}