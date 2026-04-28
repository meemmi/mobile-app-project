package com.example.pawtracker.ui.editprofile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun EditProfileScreen(
    viewModel: EditProfileViewModel,
    onNavigateBack: () -> Unit
) {
    val state by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text("Edit Dog Profile", style = MaterialTheme.typography.headlineMedium)

        TextField(value = state.name, onValueChange = viewModel::onNameChange, label = { Text("Name") }, modifier = Modifier.fillMaxWidth())
        TextField(value = state.breed, onValueChange = viewModel::onBreedChange, label = { Text("Breed") }, modifier = Modifier.fillMaxWidth())
        TextField(value = state.dailyDistanceGoal, onValueChange = viewModel::onDailyDistanceChange, label = { Text("Daily Distance (km)") }, modifier = Modifier.fillMaxWidth())
        TextField(value = state.dailyDurationGoal, onValueChange = viewModel::onDailyDurationChange, label = { Text("Daily Duration (min)") }, modifier = Modifier.fillMaxWidth())

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                viewModel.save()
                onNavigateBack()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Save and Return")
        }
    }
}