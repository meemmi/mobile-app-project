package com.example.pawtracker.ui.history

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.platform.LocalContext
import com.example.pawtracker.data.local.AppDatabase
import com.example.pawtracker.data.repository.WalkRepositoryImpl

@Composable
fun HistoryScreen(
    viewModel: HistoryViewModel,
    innerPadding: PaddingValues
) {
    val uiState by viewModel.uiState.collectAsState()

    Column(modifier = Modifier.fillMaxSize()
        .padding(innerPadding)) {

        HistoryFilterTabs(
            selected = uiState.filter,
            onSelect = { viewModel.setFilter(it) }
        )
        Spacer(modifier = Modifier.height(8.dp))

        HistoryList(walks = uiState.walks ?: emptyList(),
            modifier = Modifier.weight(1f)
            )

    }
}

@Composable
fun HistoryFilterTabs(
    selected: WalkFilter,
    onSelect: (WalkFilter) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        FilterTab("Daily", selected == WalkFilter.Daily) {
            onSelect(WalkFilter.Daily)
        }
        FilterTab("Weekly", selected == WalkFilter.Weekly) {
            onSelect(WalkFilter.Weekly)
        }
    }
}

@Composable
fun FilterTab(text: String, selected: Boolean, onClick: () -> Unit) {
    Text(
        text = text,
        modifier = Modifier
            .padding(8.dp)
            .clickable { onClick() },
        color = if (selected) MaterialTheme.colorScheme.primary else Color.Gray
    )
}

@Composable
fun HistoryList(walks: List<WalkUiModel>,  modifier: Modifier = Modifier) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(walks) { walk ->
            WalkHistoryItem(walk)
        }
    }
}

@Composable
fun WalkHistoryItem(walk: WalkUiModel) {
    val hours = walk.timeMinutes / 60
    val minutes = walk.timeMinutes % 60

    val timeFormatted = if (hours > 0)
        "%d h %02d min".format(hours, minutes)
    else
        "%d min".format(minutes)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(walk.date, style = MaterialTheme.typography.bodyLarge)
        Text("%.2f km — %s".format(walk.distanceKm, timeFormatted))
        HorizontalDivider(modifier = Modifier.padding(top = 8.dp))
    }
}


