package com.example.pawtracker.ui.tracking

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import com.example.pawtracker.R
import com.example.pawtracker.model.LocationPoint
import com.example.pawtracker.ui.theme.AppBackground
import com.example.pawtracker.ui.theme.BlueButton


@Composable
fun TrackingScreen(
    viewModel: TrackingViewModel = viewModel()
) {
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
            .background(AppBackground)
    ) {

        TrackingMap(
            points = uiState.points,
            modifier = Modifier
                .weight(1.1f)
                .fillMaxWidth()
        )

        Divider(thickness = 1.dp, color = Color.Black)

        Column(
            modifier = Modifier
                .weight(0.9f)
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {

            TrackingStatistics()

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
    points: List<LocationPoint>,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        points.forEach { point ->
        Text("lat: ${point.lat}, long: ${point.long}")
        }
    }
}

@Composable
fun TrackingStatistics(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {

        Text(stringResource(R.string.tracking_distance))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                "2.5 km",
                style = MaterialTheme.typography.headlineMedium
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(stringResource(R.string.tracking_time))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                "40 min",
                style = MaterialTheme.typography.headlineMedium
            )
        }
    }
}


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
            colors = ButtonDefaults.buttonColors(
                containerColor = BlueButton
            )
        ) {
            Text(stringResource(R.string.tracking_start))
        }

        Spacer(modifier = Modifier.width(12.dp))

        Button(
            onClick = onStop,
            enabled = tracking,
            modifier = Modifier.weight(1f),
            colors = ButtonDefaults.buttonColors(
                containerColor = BlueButton
            )
        ) {
            Text(stringResource(R.string.tracking_stop))
        }
    }
}


@Preview
@Composable
fun PreviewTracking() {
    TrackingLayout(
        uiState = TrackingUiState(),
        onStart = {},
        onStop = {}
    )
}


