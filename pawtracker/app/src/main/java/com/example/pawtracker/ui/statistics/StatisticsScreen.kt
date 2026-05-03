package com.example.pawtracker.ui.statistics

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.rememberAsyncImagePainter
import com.example.pawtracker.R
import com.example.pawtracker.ui.navigation.NavigationType
import com.example.pawtracker.ui.theme.LocalSpacing
import com.example.pawtracker.ui.theme.Spacing

// ---------------------- SCREEN ----------------------

@Composable
fun StatisticsScreen(
    viewModel: StatisticsViewModel,
    innerPadding: PaddingValues,
    navigationType: NavigationType,
    onStartWalkClick: () -> Unit
    ) {

    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val spacing = LocalSpacing.current

    if (navigationType == NavigationType.BOTTOM_NAVIGATION) {
        VerticalStatisticsContent(state, spacing, innerPadding, onStartWalkClick)
    } else {
        HorizontalStatisticsContent(state, spacing, innerPadding, onStartWalkClick)
    }
}


@Composable
fun VerticalStatisticsContent(
    state: StatisticsUiState,
    spacing: Spacing,
    innerPadding: PaddingValues,
    onStartWalkClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(innerPadding)
            .consumeWindowInsets(innerPadding)
            .verticalScroll(rememberScrollState())
            .padding(spacing.medium),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        DogProfileHeader(state, spacing)

        Spacer(modifier = Modifier.height(spacing.large))

        StatisticsCardsRow(state, Modifier.fillMaxWidth())

        Spacer(modifier = Modifier.height(spacing.medium))

        Button(
            onClick = onStartWalkClick,
            modifier = Modifier.fillMaxWidth().height(48.dp)
        ) {
            Text(stringResource(R.string.stats_start_walk_button))
        }

        Spacer(modifier = Modifier.height(spacing.extraLarge))

        TodayProgressChart(state.todayDistance, state.goalDistance.toFloat(), Modifier.size(200.dp))

        Text(
            text = stringResource(R.string.stats_progress_comparison_format, state.todayDistance, state.goalDistance),
            modifier = Modifier.padding(top = spacing.medium)
        )
    }
}

@Composable
fun HorizontalStatisticsContent(
    state: StatisticsUiState,
    spacing: Spacing,
    innerPadding: PaddingValues,
    onStartWalkClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(innerPadding)
            .verticalScroll(rememberScrollState())
            .padding(spacing.medium),
        horizontalArrangement = Arrangement.spacedBy(spacing.large),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            DogProfileHeader(state, spacing)
            Spacer(modifier = Modifier.height(spacing.medium))
            StatisticsCardsRow(state, Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(spacing.medium))
            Button(onClick = onStartWalkClick, modifier = Modifier.fillMaxWidth()) {
                Text(stringResource(R.string.stats_start_walk_button))
            }
        }


        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TodayProgressChart(state.todayDistance, state.goalDistance.toFloat(), Modifier.size(180.dp))
            Text(stringResource(R.string.stats_progress_comparison_format, state.todayDistance, state.goalDistance), modifier = Modifier.padding(top = spacing.medium))
        }
    }
}


@Composable
fun DogProfileHeader(state: StatisticsUiState, spacing: Spacing) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier.size(100.dp).clip(CircleShape).background(MaterialTheme.colorScheme.surfaceVariant),
            contentAlignment = Alignment.Center
        ) {
            val painter = if (state.imageUri.isNotEmpty()) rememberAsyncImagePainter(state.imageUri)
            else painterResource(id = R.drawable.dog1)
            Image(painter = painter, contentDescription = null, modifier = Modifier.fillMaxSize(), contentScale = ContentScale.Crop)
        }
        Text(
            text = state.dogName.ifEmpty { stringResource(R.string.profile_name_empty) },
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(top = spacing.small)
        )
    }
}

@Composable
fun StatisticsCardsRow(state: StatisticsUiState, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(LocalSpacing.current.small)
    ) {
        StatisticsCard(
            value = stringResource(R.string.stats_distance_km_format, state.todayDistance),
            label = stringResource(R.string.stats_distance_label),
            modifier = Modifier.weight(1f)
        )
        StatisticsCard(
            value = formatDuration(state.todayDuration),
            label = stringResource(R.string.stats_duration_label),
            modifier = Modifier.weight(1f)
        )
    }
}

// ---------------------- CHART ----------------------

@Composable
fun TodayProgressChart(today: Float, goal: Float, modifier: Modifier = Modifier) {

    val progress = if (goal == 0f) 0f else (today / goal).coerceIn(0f, 1f)
    val sweepAngle = progress * 360f


    val primaryColor = MaterialTheme.colorScheme.primary

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
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
fun StatisticsCard(
    value: String,
    label: String,
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.primary
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .clip(MaterialTheme.shapes.medium)
            .background(MaterialTheme.colorScheme.surface)
            .padding(LocalSpacing.current.medium)
    ) {
        Text(
            text = value,
            style = MaterialTheme.typography.titleMedium,
            color = color
        )
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
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
