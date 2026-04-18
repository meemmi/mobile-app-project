package com.example.pawtracker.ui.statistics

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

// For testing
@Composable
fun StatisticsScreen(

    viewModel: StatisticsViewModel = viewModel()
) {
    val state by viewModel.uiState.collectAsState()

    Column(modifier = Modifier.padding(20.dp)) {

        Text(text = "Picture: ${state.imageUri}")

        Text(text = "Dog's name: ${state.dogName}")

        Text(text = "Distance Today: ${state.todayDistance} km")

        Text(text = "Daily Goal: ${state.goalDistance} km")

        Text(text = "Week Total: ${state.weekDistance} km")

        Spacer(modifier = Modifier.height(20.dp))

    }
}
