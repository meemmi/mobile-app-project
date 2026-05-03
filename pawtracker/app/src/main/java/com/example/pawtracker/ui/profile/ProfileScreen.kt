package com.example.pawtracker.ui.profile

// Compose core

// Layout

// Navigation padding
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.rememberAsyncImagePainter
import com.example.pawtracker.R
import com.example.pawtracker.ui.navigation.NavigationType
import com.example.pawtracker.ui.theme.LocalSpacing


@Composable
fun ProfileScreen(
    innerPadding: PaddingValues,
    viewModel: ProfileViewModel,
    onNavigateToEdit: () -> Unit,
    navigationType: NavigationType,
    isDarkTheme: Boolean,
    onToggleTheme: () -> Unit,

) {

    val state by viewModel.uiState.collectAsStateWithLifecycle()

    if (navigationType == NavigationType.BOTTOM_NAVIGATION) {
        VerticalProfileContent(
            state = state,
            innerPadding = innerPadding,
            onNavigateToEdit = onNavigateToEdit,
            isDarkTheme = isDarkTheme,
            onToggleTheme = onToggleTheme
        )
    } else {
        HorizontalProfileContent(
            state = state,
            innerPadding = innerPadding,
            onNavigateToEdit = onNavigateToEdit,
            isDarkTheme = isDarkTheme,
            onToggleTheme = onToggleTheme
        )
    }
}

@Composable
fun HorizontalProfileContent(
    state: ProfileUiState,
    innerPadding: PaddingValues,
    onNavigateToEdit: () -> Unit,
    isDarkTheme: Boolean,
    onToggleTheme: () -> Unit

) {
    val spacing = LocalSpacing.current

    Row(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(innerPadding)
            .consumeWindowInsets(innerPadding)
            .padding(spacing.medium),
        horizontalArrangement = Arrangement.spacedBy(spacing.large)
    ) {

        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(modifier = Modifier.fillMaxWidth()) {
                IconButton(
                    onClick = onToggleTheme,
                    modifier = Modifier.align(Alignment.TopEnd)
                ) {
                    Icon(
                        imageVector = if (isDarkTheme)
                            Icons.Default.LightMode
                        else
                            Icons.Default.DarkMode,
                        contentDescription = "Toggle Theme"
                    )
                }
            }

            ProfileHeader(
                imageUri = state.imageUri,
                name = state.name.ifEmpty { stringResource(R.string.profile_name_empty) }
            )

            Spacer(modifier = Modifier.height(spacing.large))

            Button(
                onClick = onNavigateToEdit,
                modifier = Modifier.fillMaxWidth(0.8f).height(48.dp)
            ) {
                Text(stringResource(R.string.profile_edit_button))
            }
        }

        Column(
            modifier = Modifier
                .weight(1.5f)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(spacing.medium)
        ) {
            ProfileInfoCard(
                breed = state.breed.ifEmpty { stringResource(R.string.profile_breed_empty) },
                age = if (state.age.isNotEmpty()) stringResource(R.string.profile_age_format, state.age) else stringResource(R.string.profile_age_empty),
                height = if (state.height.isNotEmpty()) stringResource(R.string.profile_height_format, state.height) else stringResource(R.string.profile_height_empty),
                weight = if (state.weight.isNotEmpty()) stringResource(R.string.profile_weight_format, state.weight) else stringResource(R.string.profile_weight_empty)
            )

            DailyGoalCard(
                minutes = state.dailyDurationGoal.ifEmpty { stringResource(R.string.profile_durationgoal_empty) },
                distance = state.dailyDistanceGoal.ifEmpty { stringResource(R.string.profile_distancegoal_empty) }
            )
        }
    }
}

@Composable
fun VerticalProfileContent(
    state: ProfileUiState,
    innerPadding: PaddingValues,
    onNavigateToEdit: () -> Unit,
    isDarkTheme: Boolean,
    onToggleTheme: () -> Unit
) {
    val spacing = LocalSpacing.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(innerPadding)
            .consumeWindowInsets(innerPadding)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = spacing.medium),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(spacing.large))

        Box(modifier = Modifier.fillMaxWidth()) {
            IconButton(
                onClick = onToggleTheme,
                modifier = Modifier.align(Alignment.TopEnd)
            ) {
                Icon(
                    imageVector = if (isDarkTheme)
                        Icons.Default.LightMode
                    else
                        Icons.Default.DarkMode,
                    contentDescription = "Toggle Theme"
                )
            }
        }

        Spacer(modifier = Modifier.height(spacing.medium))

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
            modifier = Modifier.fillMaxWidth().height(48.dp)
        ) {
            Text(stringResource(R.string.profile_edit_button))
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
                    Image(
                        painter = rememberAsyncImagePainter(imageUri),
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                }

                else -> {
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
            style = MaterialTheme.typography.titleMedium,
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
                style = MaterialTheme.typography.titleLarge,
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
            color = MaterialTheme.colorScheme.primary
        )

        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}





