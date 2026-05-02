package com.example.pawtracker.ui.editprofile

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.pawtracker.R
import com.example.pawtracker.ui.theme.LocalSpacing

@Composable
fun EditProfileScreen(
    viewModel: EditProfileViewModel,
    onNavigateBack: () -> Unit,
    innerPadding: PaddingValues
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val spacing = LocalSpacing.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
            .consumeWindowInsets(innerPadding)
            .padding(horizontal = spacing.medium)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(spacing.large))

        Text(
            text = stringResource(R.string.edit_profile_title),
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
        )

        Spacer(modifier = Modifier.height(spacing.large))

        Box(
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.surfaceVariant)
                .clickable { /* Optional Feature: Profile picture implementation */ },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = stringResource(R.string.edit_profile_add_picture),
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center
            )

        }


        ProfileInputField(
            label = stringResource(R.string.edit_profile_name_label),
            value = state.name,
            onValueChange = viewModel::onNameChange
        )

        ProfileInputField(
            label = stringResource(R.string.edit_profile_age_label),
            value = state.age,
            onValueChange = viewModel::onAgeChange,
            keyboardType = KeyboardType.Number
        )

        ProfileInputField(
            label = stringResource(R.string.edit_profile_breed_label),
            value = state.breed,
            onValueChange = viewModel::onBreedChange
        )

        ProfileInputField(
            label = stringResource(R.string.edit_profile_height_label),
            value = state.height,
            onValueChange = viewModel::onHeightChange,
            keyboardType = KeyboardType.Number
        )

        ProfileInputField(
            label = stringResource(R.string.edit_profile_weight_label),
            value = state.weight,
            onValueChange = viewModel::onWeightChange,
            keyboardType = KeyboardType.Number
        )

        ProfileInputField(
            label = stringResource(R.string.edit_profile_distancegoal_label),
            value = state.dailyDistanceGoal,
            onValueChange = viewModel::onDailyDistanceChange,
            keyboardType = KeyboardType.Decimal
        )
        ProfileInputField(
            label = stringResource(R.string.edit_profile_durationgoal_label),
            value = state.dailyDurationGoal,
            onValueChange = viewModel::onDailyDurationChange,
            keyboardType = KeyboardType.Number
        )

        Spacer(modifier = Modifier.height(spacing.large))

        Button(
            onClick = {
                viewModel.save()
                onNavigateBack()
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
        ) {
            Text(stringResource(R.string.edit_profile_save_button),
            style = MaterialTheme.typography.titleMedium
            )
        }
        Spacer(modifier = Modifier.height(spacing.large))
    }
}

    @Composable
    fun ProfileInputField(
        label: String,
        value: String,
        onValueChange: (String) -> Unit,
        keyboardType: KeyboardType = KeyboardType.Text
    ) {
        val spacing = LocalSpacing.current

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = spacing.extraSmall)
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.secondary
            )

            BasicTextField(
                value = value,
                onValueChange = onValueChange,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = spacing.extraSmall),
                textStyle = MaterialTheme.typography.bodyLarge.copy(
                    color = MaterialTheme.colorScheme.onSurface
                ),
                keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
                singleLine = true
            )

            HorizontalDivider(
                thickness = 1.dp,
                color = MaterialTheme.colorScheme.outlineVariant
            )

            Spacer(modifier = Modifier.height(spacing.extraSmall))
        }
    }
