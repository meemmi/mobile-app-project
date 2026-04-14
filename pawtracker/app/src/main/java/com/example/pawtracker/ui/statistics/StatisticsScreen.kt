package com.example.pawtracker.ui.statistics

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp

// ---------------------- SCREEN ----------------------

@Composable
fun StatisticsScreen(viewModel: StatisticsViewModel) {

    val state by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // Dog picture (placeholder)
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

        Spacer(modifier = Modifier.height(20.dp))

        // Distance + Duration
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

        Spacer(modifier = Modifier.height(20.dp))

        // Start Walk button
        Button(
            onClick = { /* TODO: navigate to tracking */ },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Start Walk")
        }

        Spacer(modifier = Modifier.height(30.dp))

        // ✅ CHART
        TodayProgressChart(
            today = state.todayDistance,
            goal = state.goalDistance.toFloat()
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = "%.2f / %.2f km".format(
                state.todayDistance,
                state.goalDistance
            ),
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

// ---------------------- CHART ----------------------

@Composable
fun TodayProgressChart(today: Float, goal: Float) {

    val progress = if (goal == 0f) 0f else (today / goal).coerceIn(0f, 1f)
    val sweepAngle = progress * 360f

    // ✅ FIX: get color OUTSIDE Canvas
    val primaryColor = MaterialTheme.colorScheme.primary

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.size(200.dp)
    ) {

        Canvas(modifier = Modifier.fillMaxSize()) {

            // Background circle
            drawArc(
                color = Color.LightGray,
                startAngle = 0f,
                sweepAngle = 360f,
                useCenter = false,
                style = Stroke(width = 30f)
            )

            // Progress arc
            drawArc(
                color = primaryColor,
                startAngle = -90f,
                sweepAngle = sweepAngle,
                useCenter = false,
                style = Stroke(
                    width = 30f,
                    cap = StrokeCap.Round
                )
            )
        }

        // Center percentage text
        Text(
            text = "${(progress * 100).toInt()}%",
            style = MaterialTheme.typography.headlineMedium
        )
    }
}

// ---------------------- STAT CARD ----------------------

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

// ---------------------- FORMAT TIME ----------------------

fun formatDuration(ms: Long): String {
    val totalMinutes = ms / 60000
    val hours = totalMinutes / 60
    val minutes = totalMinutes % 60

    return if (hours > 0)
        "%d h %02d min".format(hours, minutes)
    else
        "%d min".format(minutes)
}