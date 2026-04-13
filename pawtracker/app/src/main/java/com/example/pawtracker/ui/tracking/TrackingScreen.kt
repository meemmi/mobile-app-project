package com.example.pawtracker.ui.tracking

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.JointType
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.RoundCap

// --- Compose Maps imports (ONLY these!) ---
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.Polyline
import com.google.maps.android.compose.rememberCameraPositionState
/*import com.google.maps.android.compose.CameraUpdateFactory
import com.google.maps.android.compose.LatLng
import com.google.maps.android.compose.JointType
import com.google.maps.android.compose.RoundCap*/
// ------------------------------------------

@Composable
fun TrackingScreen(viewModel: TrackingViewModel = viewModel()) {
    val uiState by viewModel.uiState.collectAsState()

    TrackingLayout(
        uiState = uiState,
        onStart = { viewModel.startTracking() },
        onStop = { viewModel.stopTracking() }
    )
}

@Composable
fun TrackingLayout(
    uiState: TrackingUiState,
    onStart: () -> Unit,
    onStop: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {

        TrackingMap(
            uiState = uiState,
            modifier = Modifier.weight(1f)
        )

        Divider(thickness = 1.dp, color = Color.Black)

        Column(
            modifier = Modifier
                .weight(0.9f)
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {

            TrackingStatistics(uiState = uiState)

            ControlButtons(
                tracking = uiState.tracking,
                onStart = onStart,
                onStop = onStop
            )
        }
    }
}

@Composable
fun TrackingMap(
    uiState: TrackingUiState,
    modifier: Modifier = Modifier
) {
    val cameraPositionState = rememberCameraPositionState()

    // Move camera when location updates
    LaunchedEffect(uiState.currentLocation) {
        uiState.currentLocation?.let { point ->
            cameraPositionState.animate(
                CameraUpdateFactory.newLatLngZoom(
                    LatLng(point.latitude, point.longitude),
                    17f
                )
            )
        }
    }

    GoogleMap(
        modifier = modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState,
        properties = MapProperties(
            isMyLocationEnabled = uiState.locationPermission
        ),
        uiSettings = MapUiSettings(
            zoomControlsEnabled = false,
            myLocationButtonEnabled = true
        )
    ) {

        // Draw polyline
        if (uiState.points.size > 1) {

            val filteredPoints = uiState.points.distinctBy {
                Pair(it.latitude, it.longitude)
            }

            Polyline(
                points = filteredPoints.map {
                    LatLng(it.latitude, it.longitude)
                },
                color = Color(0xFF1976D2),
                width = 12f,
                geodesic = true,
                jointType = JointType.ROUND,
                startCap = RoundCap(),
                endCap = RoundCap()
            )
        }

        // Current location marker
        uiState.currentLocation?.let { point ->
            Marker(
                state = MarkerState(
                    position = LatLng(point.latitude, point.longitude)
                ),
                title = "Dog 🐕"
            )
        }
    }
}

@Composable
fun TrackingStatistics(
    uiState: TrackingUiState,
    modifier: Modifier = Modifier
) {
    val totalMinutes = uiState.time / 60000
    val hours = totalMinutes / 60
    val minutes = totalMinutes % 60

    val formattedTime = if (hours > 0)
        "%d h %02d min".format(hours, minutes)
    else
        "%d min".format(minutes)

    Column(modifier = modifier.fillMaxWidth()) {

        Text("Distance")
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                "%.2f km".format(uiState.distance),
                style = MaterialTheme.typography.headlineMedium
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text("Time")
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                formattedTime,
                style = MaterialTheme.typography.headlineMedium
            )
        }
    }
}

@Composable
fun ControlButtons(
    tracking: Boolean,
    onStart: () -> Unit,
    onStop: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Button(
            onClick = onStart,
            enabled = !tracking,
            modifier = Modifier.weight(1f)
        ) {
            Text("Start")
        }

        Spacer(modifier = Modifier.width(12.dp))

        Button(
            onClick = onStop,
            enabled = tracking,
            modifier = Modifier.weight(1f)
        ) {
            Text("Stop")
        }
    }
}
