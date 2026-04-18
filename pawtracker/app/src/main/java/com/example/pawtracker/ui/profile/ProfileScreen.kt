package com.example.pawtracker.ui.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

// For testing
@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = viewModel()
) {
    val state by viewModel.uiState.collectAsState()

    Column(modifier = Modifier.padding(16.dp)) {

        Text("Dog Profile")

        TextField(
            value = state.name,
            onValueChange = viewModel::onNameChange,
            label = { Text("Name") }
        )

        TextField(
            value = state.breed,
            onValueChange = viewModel::onBreedChange,
            label = { Text("Breed") }
        )

        TextField(
            value = state.dailyDistanceGoal,
            onValueChange = viewModel::onDailyDistanceChange,
            label = { Text("Daily distance") }
        )

        TextField(
            value = state.dailyDurationGoal,
            onValueChange = viewModel::onDailyDurationChange,
            label = { Text("Daily duration") }
        )

        TextField(
            value = state.weeklyDistanceGoal,
            onValueChange = viewModel::onWeeklyDistanceChange,
            label = { Text("Weekly distance") }
        )

        TextField(
            value = state.weeklyDurationGoal,
            onValueChange = viewModel::onWeeklyDurationChange,
            label = { Text("Weekly duration") }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = viewModel::save) {
            Text("Save")
        }
    }
}