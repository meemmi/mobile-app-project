package com.example.pawtracker.ui.statistics

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue

// For testing
@Composable
fun StatisticsScreen(
    viewModel: StatisticsViewModel
) {
    val state by viewModel.uiState.collectAsState()

    Text(text = "Today distance: ${state.todayDistance}")
}