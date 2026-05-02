package com.example.pawtracker.ui.tracking

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.pawtracker.R
import com.example.pawtracker.ui.theme.LocalSpacing
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.Polyline
import com.google.maps.android.compose.rememberCameraPositionState


@Composable
fun TrackingScreen(
    innerPadding: PaddingValues,
    viewModel: TrackingViewModel
    ) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    TrackingLayout(
        uiState = uiState,
        onStart = { viewModel.startTracking() },
        onStop = { viewModel.stopTracking() },
        innerPadding = innerPadding

    )
}

/**
 * Layout: Map + stats + control buttons
 */
@Composable
fun TrackingLayout(
    uiState: TrackingUiState,
    onStart: () -> Unit,
    onStop: () -> Unit,
    innerPadding: PaddingValues
) {
    val spacing = LocalSpacing.current

    Column(
        modifier = Modifier
           .fillMaxSize()
           .background(MaterialTheme.colorScheme.primaryContainer)
           .padding(innerPadding)
           .consumeWindowInsets(innerPadding)
    ) {

        TrackingMap(
            uiState = uiState,
            modifier = Modifier.weight(2f)
        )

        HorizontalDivider(
            thickness = 1.dp,
            color = MaterialTheme.colorScheme.outlineVariant
        )

        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(spacing.medium),
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
    //initial position before real GPS location arrives.
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(
            LatLng(60.1699, 24.9384),
            15f
        )
    }
    // Move camera when location updates
    LaunchedEffect(uiState.currentLocation) {
        uiState.currentLocation?.let { point ->
            cameraPositionState.position = CameraPosition.fromLatLngZoom(
                    LatLng(point.latitude, point.longitude),
                    15f
            )
        }
    }

    GoogleMap(
        modifier = modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState,
        properties = MapProperties(
            isMyLocationEnabled = uiState.locationPermission),
        uiSettings = MapUiSettings(
            zoomControlsEnabled = false,
            myLocationButtonEnabled = true
        )
    ) {
        Polyline(
            points = uiState.points.map {
                LatLng(it.latitude, it.longitude)
            }
        )

        uiState.currentLocation?.let { point ->
            Marker(
                state = MarkerState(
                    position = LatLng(point.latitude, point.longitude)
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
    val spacing = LocalSpacing.current

    val totalMinutes = uiState.time / 60000
    val hours = totalMinutes / 60
    val minutes = totalMinutes % 60

    val formattedTime = if (hours > 0) {
        stringResource(R.string.tracking_duration_long_format, hours, minutes)
    } else {
        stringResource(R.string.tracking_duration_short_format, minutes)
    }
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.medium)
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .padding(spacing.extraLarge),
        verticalArrangement = Arrangement.spacedBy(spacing.small)
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.tracking_distance),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
            )
            Text(
                text = "%.2f km".format(uiState.distance),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        HorizontalDivider(
            thickness = 0.5.dp,
            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.2f)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.tracking_time),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
            )
            Text(
                text = formattedTime,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
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
