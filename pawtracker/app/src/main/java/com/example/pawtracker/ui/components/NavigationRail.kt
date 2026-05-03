package com.example.pawtracker.ui.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Assessment
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.pawtracker.R
import com.example.pawtracker.ui.navigation.NavigationType
import com.example.pawtracker.ui.navigation.Screen

@Composable
fun NavigationRail(
    navController: NavHostController,
    navigationType: NavigationType,
    hasCompletedOnboarding: Boolean,
    modifier: Modifier = Modifier
) {
    if (!hasCompletedOnboarding) return

    // Haetaan nykyinen reitti, jotta voimme merkitä valitun ikonin
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationRail(
        modifier = modifier
    ) {

        Spacer(Modifier.weight(1f))

        // 1. TILASTOT
        NavigationRailItem(
            selected = currentRoute == Screen.Statistics.route,
            onClick = {
                navController.navigate(Screen.Statistics.route) {
                    popUpTo(navController.graph.startDestinationId) { saveState = true }
                    launchSingleTop = true
                    restoreState = true
                }
            },
            icon = {
                Icon(Icons.Filled.BarChart, contentDescription = "statistics")
            },
            label = { Text("stats") }
        )

        // 2. KARTTA / SEURANTA
        NavigationRailItem(
            selected = currentRoute == Screen.Tracking.route,
            onClick = {
                navController.navigate(Screen.Tracking.route) {
                    popUpTo(navController.graph.startDestinationId) { saveState = true }
                    launchSingleTop = true
                    restoreState = true
                }
            },
            icon = {
                Icon(Icons.Filled.Place, contentDescription = "map")
            },
            label = { Text("map") }
        )

        // 3. HISTORIA
        NavigationRailItem(
            selected = currentRoute == Screen.History.route,
            onClick = {
                navController.navigate(Screen.History.route) {
                    popUpTo(navController.graph.startDestinationId) { saveState = true }
                    launchSingleTop = true
                    restoreState = true
                }
            },
            icon = {
                Icon(Icons.Filled.History, contentDescription = "history")
            },
            label = { Text("history") }
        )

        // 4. PROFIILI
        NavigationRailItem(
            selected = currentRoute == Screen.Profile.route,
            onClick = {
                navController.navigate(Screen.Profile.route) {
                    popUpTo(navController.graph.startDestinationId) { saveState = true }
                    launchSingleTop = true
                    restoreState = true
                }
            },
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.navbar_dog_icon),
                    contentDescription = "profile",
                    modifier = Modifier.size(24.dp),
                    tint = Color.Unspecified
                )
            },
            label = { Text("profile") }
        )

        Spacer(Modifier.weight(1f))
    }
}