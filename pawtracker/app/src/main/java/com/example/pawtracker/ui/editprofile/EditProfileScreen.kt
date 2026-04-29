package com.example.pawtracker.ui.editprofile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun EditProfileScreen(
    viewModel: EditProfileViewModel,
    onNavigateBack: () -> Unit,
    innerPadding: PaddingValues
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
            .consumeWindowInsets(innerPadding)
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text("Edit Dog Profile", style = MaterialTheme.typography.headlineMedium)

        TextField(
            value = state.name,
            onValueChange = viewModel::onNameChange,
            label = { Text("Name") },
            modifier = Modifier.fillMaxWidth()
        )

        TextField(
            value = state.age,
            onValueChange = viewModel::onAgeChange,
            label = { Text("Age") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        TextField(
            value = state.breed,
            onValueChange = viewModel::onBreedChange,
            label = { Text("Breed") },
            modifier = Modifier.fillMaxWidth()
        )

        TextField(
            value = state.height,
            onValueChange = viewModel::onHeightChange,
            label = { Text("Height (cm)") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        TextField(
            value = state.weight,
            onValueChange = viewModel::onWeightChange,
            label = { Text("Weight (kg)") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        TextField(
            value = state.dailyDistanceGoal,
            onValueChange = viewModel::onDailyDistanceChange,
            label = { Text("Daily Distance Goal (km)") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
        )

        TextField(
            value = state.dailyDurationGoal,
            onValueChange = viewModel::onDailyDurationChange,
            label = { Text("Daily Duration Goal (min)") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
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