package com.example.pawtracker.ui.profile

// Compose core
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.collectAsState

// Layout
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background

// Shapes & drawing
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.draw.clip

// Material 3
import androidx.compose.material3.*

// Icons
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pets
import androidx.compose.material.icons.filled.Cake
import androidx.compose.material.icons.filled.Height
import androidx.compose.material.icons.filled.MonitorWeight
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter

// Navigation padding
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import com.example.pawtracker.R
import com.example.pawtracker.data.local.AppDatabase
import com.example.pawtracker.data.repository.DogProfileRepositoryImpl

@Composable
fun ProfileScreen(
    innerPadding: PaddingValues,
    viewModel: ProfileViewModel,
    onNavigateToEdit: () -> Unit
) {

    val state by viewModel.dogProfile.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primaryContainer) // Figma background
            .padding(innerPadding)
            .consumeWindowInsets(innerPadding)
            .padding(horizontal = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(24.dp))

        ProfileHeader(
            imageUri = state?.imageUri ?: "",
            name = state?.name ?: "Dog"
        )

        Spacer(modifier = Modifier.height(24.dp))

        ProfileInfoCard(
            breed = state?.breed ?: "",
            age = "4 years old",
            height = "23–24 inches (male)",
            weight = "65–75 pounds"
        )

        Spacer(modifier = Modifier.height(20.dp))

        DailyGoalCard(
            minutes = state?.dailyDurationGoal?.toString() ?: "0",
            distance = state?.dailyDistanceGoal?.toString() ?: "0.0"
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = onNavigateToEdit,
            shape = RoundedCornerShape(14.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary
            )
        ) {
            Text(
                "Edit profile",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}
@Composable
fun ProfileHeader(
    imageUri: String,
    name: String
) {
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
        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = if (name.isNotEmpty()) name else "Your Dog",
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
    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(20.dp)) {

            InfoRow(R.drawable.dog_age, age)
            InfoRow(R.drawable.dog_height, height)
            InfoRow(R.drawable.dog_weight, weight)
        }
    }
}
@Composable
fun InfoRow(iconRes: Int, text: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Icon(
            painter = painterResource(id = iconRes),
            contentDescription = null,
            tint = Color.Unspecified,
            modifier = Modifier.size(24.dp)
        )

        Spacer(modifier = Modifier.width(12.dp))

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
    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "Daily Activity Goal",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {

                GoalItem(
                    value = "$minutes min",
                    label = "Duration"
                )

                GoalItem(
                    value = "$distance km",
                    label = "Distance"
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





