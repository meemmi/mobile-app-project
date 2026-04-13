package com.example.pawtracker.ui.tracking

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pawtracker.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.maps.android.compose.*

/**
 * Main TrackingScreen composable
 */
@Composable
fun TrackingScreen(viewModel: TrackingViewModel = viewModel()) {
    val uiState by viewModel.uiState.collectAsState()

    TrackingLayout(
        uiState = uiState,
        onStart = { viewModel.startTracking() },
        onStop = { viewModel.stopTracking() }
    )
}

/**
 * Layout: Map + stats + control buttons
 */
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

/**
 * Google Map showing tracking points
 */
@Composable
fun TrackingMap(
    uiState: TrackingUiState,
    modifier: Modifier = Modifier
) {
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(
            com.google.android.gms.maps.model.LatLng(60.1699, 24.9384),
            15f
        )
    }

    LaunchedEffect(uiState.currentLocation) {
        uiState.currentLocation?.let { point ->
            cameraPositionState.animate(
                CameraUpdateFactory.newLatLng(
                    com.google.android.gms.maps.model.LatLng(point.latitude, point.longitude)
                )
            )
        }
    }

    GoogleMap(
        modifier = modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState,
        properties = MapProperties(isMyLocationEnabled = uiState.locationPermission),
        uiSettings = MapUiSettings(
            zoomControlsEnabled = false,
            myLocationButtonEnabled = true
        )
    ) {
        Polyline(
            points = uiState.points.map { com.google.android.gms.maps.model.LatLng(it.latitude, it.longitude) }
        )

        uiState.currentLocation?.let { point ->
            Marker(
                state = MarkerState(
                    position = com.google.android.gms.maps.model.LatLng(point.latitude, point.longitude)
                ),
                title = "Current"
            )
        }
    }
}

/**
 * Show statistics below the map
 */
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

    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        Text(stringResource(R.string.tracking_distance))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            contentAlignment = Alignment.Center
        ) {
            Text("%.2f km".format(uiState.distance), style = MaterialTheme.typography.headlineMedium)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(stringResource(R.string.tracking_time))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(formattedTime, style = MaterialTheme.typography.headlineMedium)
        }
    }
}

/**
 * Start/Stop buttons
 */
@Composable
fun ControlButtons(
    tracking: Boolean,
    onStart: () -> Unit,
    onStop: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Button(
            onClick = onStart,
            enabled = !tracking,
            modifier = Modifier.weight(1f),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
        ) {
            Text(stringResource(R.string.tracking_start))
        }

        Spacer(modifier = Modifier.width(12.dp))

        Button(
            onClick = onStop,
            enabled = tracking,
            modifier = Modifier.weight(1f),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
        ) {
            Text(stringResource(R.string.tracking_stop))
        }
    }
}