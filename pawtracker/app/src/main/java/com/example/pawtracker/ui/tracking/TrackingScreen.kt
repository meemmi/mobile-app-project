package com.example.pawtracker.ui.tracking

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
//This is just reading StateFlow and calling startTracking() / stopTracking()
@Composable
fun TrackingScreen(
    viewModel: TrackingViewModel
) {
    val location by viewModel.currentLocation.collectAsState(initial = null)

    Column {


        Button(onClick = { viewModel.startTracking() }) {
            Text("Start tracking")
        }

        Button(onClick = { viewModel.stopTracking() }) {
            Text("Stop tracking")
        }
        Text("Lat: ${location?.latitude ?: "-"}")
        Text("Lng: ${location?.longitude ?: "-"}")
    }
}