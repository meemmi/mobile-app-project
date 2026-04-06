package com.example.pawtracker.ui.history

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel


@Composable
fun HistoryScreen(viewModel: HistoryViewModel = viewModel()) {
    val uiState by viewModel.uiState.collectAsState()

    Column(modifier = Modifier.fillMaxSize()) {

        HistoryFilterTabs(
            selected = uiState.filter,
            onSelect = { viewModel.setFilter(it) }
        )

        HistoryList(walks = uiState.walks)
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
fun HistoryList(walks: List<WalkUiModel>) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(walks) { walk ->
            WalkHistoryItem(walk)
        }
    }
}

@Composable
fun WalkHistoryItem(walk: WalkUiModel) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(walk.date, style = MaterialTheme.typography.titleMedium)
        Text("${walk.distanceKm} km — ${walk.timeMinutes} min")
        Divider(modifier = Modifier.padding(top = 8.dp))
    }
}


