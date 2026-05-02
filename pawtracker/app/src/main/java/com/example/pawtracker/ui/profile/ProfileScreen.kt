package com.example.pawtracker.ui.profile

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.rememberAsyncImagePainter
import com.example.pawtracker.R
import com.example.pawtracker.ui.theme.LocalSpacing


@Composable
fun ProfileScreen(
    innerPadding: PaddingValues,
    viewModel: ProfileViewModel,
    onNavigateToEdit: () -> Unit
) {

    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val spacing = LocalSpacing.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primaryContainer)
            .padding(innerPadding)
            .consumeWindowInsets(innerPadding)
            .padding(horizontal = spacing.medium),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(spacing.large))

        ProfileHeader(
            imageUri = state.imageUri,
            name = state.name.ifEmpty { stringResource(R.string.profile_name_empty) }
        )

        Spacer(modifier = Modifier.height(spacing.medium))

        ProfileInfoCard(
            breed = state.breed.ifEmpty { stringResource(R.string.profile_breed_empty) },
            age = if (state.age.isNotEmpty()) {
                stringResource(R.string.profile_age_format, state.age)
            } else {
                stringResource(R.string.profile_age_empty)
            },
            height = if (state.height.isNotEmpty()) {
                stringResource(R.string.profile_height_format, state.height)
            } else {
                stringResource(R.string.profile_height_empty)
            },
            weight = if (state.weight.isNotEmpty()) {
                stringResource(R.string.profile_weight_format, state.weight)
            } else {
                stringResource(R.string.profile_weight_empty)
            }
        )

        Spacer(modifier = Modifier.height(spacing.medium))

        DailyGoalCard(
            minutes = state.dailyDurationGoal.ifEmpty { stringResource(R.string.profile_durationgoal_empty) },
            distance = state.dailyDistanceGoal.ifEmpty { stringResource(R.string.profile_distancegoal_empty) }
        )

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = onNavigateToEdit,
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary
            )
        ) {
            Text(
                stringResource(R.string.profile_edit_button),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }

        Spacer(modifier = Modifier.height(spacing.medium))
    }
}
@Composable
fun ProfileHeader(
    imageUri: String,
    name: String
) {
    val spacing = LocalSpacing.current
    Column(horizontalAlignment = Alignment.CenterHorizontally) {

        Box(
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.surface),
            contentAlignment = Alignment.Center
        ) {
            when {
                imageUri.isNotEmpty() -> {
                    // User-selected image
                    Image(
                        painter = rememberAsyncImagePainter(imageUri),
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                }

                else -> {
                    // Default local drawable (dog1.jpg)
                    Image(
                        painter = painterResource(id = R.drawable.dog1),
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(spacing.medium))

        Text(
            text = if (name.isNotEmpty()) name else stringResource(R.string.profile_name_empty),
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
    }
}
@Composable
fun ProfileInfoCard(
    breed: String,
    age: String,
    height: String,
    weight: String
) {
    val spacing = LocalSpacing.current

    Card(
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(spacing.medium)) {
            InfoRow(R.drawable.dog_icon, stringResource(R.string.profile_label_breed), breed)
            InfoRow(R.drawable.dog_age, stringResource(R.string.profile_label_age), age)
            InfoRow(R.drawable.dog_height, stringResource(R.string.profile_label_height), height)
            InfoRow(R.drawable.dog_weight, stringResource(R.string.profile_label_weight), weight)
        }
    }
}
@Composable
fun InfoRow(iconRes: Int, label: String, text: String) {
    val spacing = LocalSpacing.current
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = spacing.small),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Icon(
            painter = painterResource(id = iconRes),
            contentDescription = null,
            tint = Color.Unspecified,
            modifier = Modifier.size(22.dp)
        )

        Spacer(modifier = Modifier.width(spacing.medium))

        Text(
            text = "$label: ",
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )

        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Composable
fun DailyGoalCard(
    minutes: String,
    distance: String
) {
    val spacing = LocalSpacing.current

    Card(
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(spacing.medium),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = stringResource(R.string.profile_daily_goal_title),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(spacing.medium))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {

                GoalItem(
                    value = stringResource(R.string.profile_duration_format, minutes),
                    label = stringResource(R.string.profile_duration_label)
                )

                GoalItem(
                    value = stringResource(R.string.profile_distance_format, distance),
                    label = stringResource(R.string.profile_distance_label)
                )
            }
        }
    }
}
@Composable
fun GoalItem(value: String, label: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {

        Text(
            text = value,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )

        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}





