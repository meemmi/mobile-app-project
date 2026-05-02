package com.example.pawtracker.ui.statistics

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.pawtracker.R
import com.example.pawtracker.ui.theme.LocalSpacing

// ---------------------- SCREEN ----------------------

@Composable
fun StatisticsScreen(
    viewModel: StatisticsViewModel,
    innerPadding: PaddingValues,
    onStartWalkClick: () -> Unit
    ) {

    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val spacing = LocalSpacing.current


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primaryContainer)
            .padding(innerPadding)
            .consumeWindowInsets(innerPadding)
            .padding(spacing.medium),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // Dog picture (placeholder)
        Box(
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.surfaceVariant)
        )

        // Dog name
        Text(
            text = state.dogName.ifEmpty { stringResource(R.string.profile_name_empty) },
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = if (state.dogName.isNotBlank())
                MaterialTheme.colorScheme.primary
            else
                MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(top = spacing.small)
        )

        Spacer(modifier = Modifier.height(spacing.small))

        Text(
            text = stringResource(R.string.stats_today_activity_label),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(spacing.large))

        // Distance + Duration
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            StatCard(
                value = stringResource(R.string.stats_distance_km_format, state.todayDistance),
                label = stringResource(R.string.stats_distance_label)
            )
            StatCard(
                value = formatDuration(state.todayDuration),
                label = stringResource(R.string.stats_duration_label)
            )
        }

        Spacer(modifier = Modifier.height(spacing.medium))

        // Start Walk button
        Button(
            onClick = { onStartWalkClick() },
            modifier = Modifier.fillMaxWidth().height(48.dp)
        ) {
            Text(stringResource(R.string.stats_start_walk_button))
        }

        Spacer(modifier = Modifier.height(spacing.extraLarge))

        // CHART
        TodayProgressChart(
            today = state.todayDistance,
            goal = state.goalDistance.toFloat()
        )

        Spacer(modifier = Modifier.height(spacing.medium))

        Text(
            text = stringResource(
                R.string.stats_progress_comparison_format,
                state.todayDistance,
                state.goalDistance
            ),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onBackground
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
            .clip(MaterialTheme.shapes.medium)
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .padding(LocalSpacing.current.medium)
            .width(140.dp)
    ) {
        Text(value, style = MaterialTheme.typography.titleMedium)
        Text(label, color = MaterialTheme.colorScheme.onSurfaceVariant)
    }
}

// ---------------------- FORMAT TIME ----------------------
@Composable
fun formatDuration(ms: Long): String {
    val totalMinutes = ms / 60000
    val hours = totalMinutes / 60
    val minutes = totalMinutes % 60

    return if (hours > 0) {
        stringResource(R.string.stats_duration_long_format, hours, minutes)
    } else {
        stringResource(R.string.stats_duration_short_format, minutes)
    }
}