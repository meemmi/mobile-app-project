package com.example.pawtracker.ui.statistics

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


@Composable
fun StatisticsScreen(viewModel: StatisticsViewModel) {
    val state by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // Dog picture
        Box(
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape)
                .background(Color.LightGray)
        )

        // Dog name
        Text(
            text = state.dogName,
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(top = 8.dp)
        )

        Text(
            text = "Today's activity",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray
        )

        Spacer(Modifier.height(20.dp))

        // Distance + Duration cards
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            StatCard(
                value = "%.2f km".format(state.todayDistance),
                label = "Distance"
            )
            StatCard(
                value = formatDuration(state.todayDuration),
                label = "Duration"
            )
        }

        Spacer(Modifier.height(20.dp))

        // Start Walk button
        Button(
            onClick = { /* navigate to tracking */ },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Start Walk")
        }

        Spacer(Modifier.height(30.dp))

        // Pie chart
        PieChart(
            today = state.todayDistance,
            goal = state.goalDistance.toFloat()
        )

        Spacer(Modifier.height(10.dp))

        Text(
            text = "%.2f / %.2f km".format(state.todayDistance, state.goalDistance),
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Composable
fun StatCard(value: String, label: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .padding(16.dp)
            .width(140.dp)
    ) {
        Text(value, style = MaterialTheme.typography.titleMedium)
        Text(label, color = Color.Gray)
    }
}

@Composable
fun PieChart(today: Float, goal: Float) {
    val progress = if (goal == 0f) 0f else (today / goal).coerceIn(0f, 1f)

    Box(
        modifier = Modifier
            .size(180.dp)
            .clip(CircleShape)
            .background(Color.LightGray),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize(progress)
                .background(MaterialTheme.colorScheme.primary)
        )
    }
}


fun formatDuration(ms: Long): String {
    val totalMinutes = ms / 60000
    val hours = totalMinutes / 60
    val minutes = totalMinutes % 60

    return if (hours > 0)
        "%d h %02d min".format(hours, minutes)
    else
        "%d min".format(minutes)
}


