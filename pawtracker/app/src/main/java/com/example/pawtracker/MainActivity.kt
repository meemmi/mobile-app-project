package com.example.pawtracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.pawtracker.data.repository.GPSRepository
import com.example.pawtracker.ui.theme.PawTrackerTheme
import com.example.pawtracker.ui.tracking.TrackingScreen
import com.example.pawtracker.ui.tracking.TrackingViewModel


class MainActivity : ComponentActivity() {
    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
            // You can show a message if needed
        }

    private fun requestLocationPermission() {
        requestPermissionLauncher.launch(android.Manifest.permission.ACCESS_FINE_LOCATION)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 1. Ask for permission
        requestLocationPermission()

        // 2. Create repository + viewmodel
        val gpsRepository = GPSRepository(this)
        val trackingViewModel = TrackingViewModel(gpsRepository)
        enableEdgeToEdge()
        setContent {
            PawTrackerTheme {
                //shows GPS screen
                TrackingScreen(viewModel = trackingViewModel)

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