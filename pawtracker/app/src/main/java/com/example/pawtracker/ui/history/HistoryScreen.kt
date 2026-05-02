package com.example.pawtracker.ui.history

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.pawtracker.R
import com.example.pawtracker.ui.theme.LocalSpacing

@Composable
fun HistoryScreen(
    innerPadding: PaddingValues,
    viewModel: HistoryViewModel
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val spacing = LocalSpacing.current

    Column(modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.primaryContainer)
        .padding(innerPadding)
        .consumeWindowInsets(innerPadding)
        .padding(horizontal = spacing.medium)
    ) {

        Spacer(modifier = Modifier.height(spacing.large))

        HistoryFilterTabs(
            selected = uiState.filter,
            onSelect = { viewModel.setFilter(it) }
        )
        Spacer(modifier = Modifier.height(spacing.medium))

        if (uiState.walks.isNullOrEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(R.string.history_empty_label),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        } else {

            HistoryList(
                walks = uiState.walks,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
fun HistoryFilterTabs(
    selected: WalkFilter,
    onSelect: (WalkFilter) -> Unit
) {
    val spacing = LocalSpacing.current

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = spacing.small),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        FilterTab(
            text = stringResource(R.string.history_filter_daily),
            selected = selected == WalkFilter.Daily,
            modifier = Modifier.weight(1f)
        ) {
            onSelect(WalkFilter.Daily)
        }

        Spacer(modifier = Modifier.width(spacing.medium))

        FilterTab(
            text = stringResource(R.string.history_filter_weekly),
            selected = selected == WalkFilter.Weekly,
            modifier = Modifier.weight(1f)
        ) {
            onSelect(WalkFilter.Weekly)
        }
    }
}

@Composable
fun FilterTab(
    text: String,
    selected: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    if (selected) {
        Button(
            onClick = onClick,
            modifier = modifier,
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary
            )
        ) {
            Text(text = text)
        }
    } else {
        OutlinedButton(
            onClick = onClick,
            modifier = modifier,
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary)
        ) {
            Text(
                text = text,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Composable
fun HistoryList(walks: List<WalkUiModel>,  modifier: Modifier = Modifier) {
    LazyColumn(modifier = modifier.fillMaxSize()) {
        items(walks) { walk ->
            WalkHistoryItem(walk)
        }
    }
}

@Composable
fun WalkHistoryItem(walk: WalkUiModel) {
    val spacing = LocalSpacing.current
    val hours = walk.timeMinutes / 60
    val minutes = walk.timeMinutes % 60

    val timeFormatted = if (hours > 0) {
        stringResource(R.string.history_item_duration_long_label, hours, minutes)
    } else {
        stringResource(R.string.history_item_duration_short_label, minutes)
    }

    val distanceFormatted = stringResource(R.string.history_item_distance_label, walk.distanceKm)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(spacing.medium)
    ) {
        Text(walk.date, style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.primary)
        Text(text = stringResource(R.string.history_item_combined_label, distanceFormatted, timeFormatted),)
        HorizontalDivider(modifier = Modifier.padding(top = spacing.extraSmall))
    }
}


