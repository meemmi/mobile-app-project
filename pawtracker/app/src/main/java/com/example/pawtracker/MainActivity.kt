package com.example.pawtracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.pawtracker.data.repository.GPSRepository
import com.example.pawtracker.ui.theme.PawTrackerTheme
import com.example.pawtracker.ui.tracking.TrackingScreen
import com.example.pawtracker.ui.tracking.TrackingViewModel
import com.example.pawtracker.data.repository.WalkRepository
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Text
import androidx.room.Room
import kotlin.getValue
import com.example.pawtracker.data.local.AppDatabase
import com.example.pawtracker.ui.history.HistoryScreen
import com.example.pawtracker.ui.history.HistoryViewModel


class MainActivity : ComponentActivity() {

    // 1. Create repository + ViewModel
    private val database by lazy {
        Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "walk_db"
        ).build()
    }

    private val gpsRepository by lazy { GPSRepository(this) }

    private val walkRepository by lazy {WalkRepository(database.walkDao())}

    private val historyViewModel by lazy { HistoryViewModel(walkRepository) }

    private val trackingViewModel by lazy { TrackingViewModel(gpsRepository, walkRepository) }

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
               HistoryScreen(viewModel = historyViewModel)
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