package com.example.pawtracker.ui.components

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
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
import com.example.pawtracker.ui.navigation.Screen


@Composable
fun NavBar(navController: NavHostController, has_completed_onboarding: Boolean) {

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route


    if (currentRoute != Screen.Main.route) {
        NavigationBar(
            modifier = Modifier.navigationBarsPadding(),
            windowInsets = WindowInsets.navigationBars,
        ) {

            NavigationBarItem(
                selected = currentRoute == Screen.Statistics.route,
                onClick = {
                    val target = if (has_completed_onboarding) Screen.Statistics.route else Screen.Main.route
                    navController.navigate(target) {
                        popUpTo(navController.graph.startDestinationId) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = {
                    Icon(
                        Icons.Filled.BarChart,
                        contentDescription = "statistics",
                        modifier = Modifier
                            .size(24.dp)
                    )
                },
                label = { Text("stats") }
            )


            NavigationBarItem(
                selected = currentRoute == Screen.Tracking.route,
                onClick = {
                    navController.navigate(Screen.Tracking.route) {
                        popUpTo(navController.graph.startDestinationId) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = {
                    Icon(
                        Icons.Filled.Place, contentDescription = "map",
                        modifier = Modifier
                            .size(24.dp)
                    )
                },
                label = { Text("map") }
            )

            NavigationBarItem(
                selected = currentRoute == Screen.History.route,
                onClick = {
                    navController.navigate(Screen.History.route) {
                        popUpTo(navController.graph.startDestinationId) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = {
                    Icon(
                        Icons.Filled.History, contentDescription = "history",
                        modifier = Modifier
                            .size(24.dp)
                    )
                },
                label = { Text("history") }
            )

            NavigationBarItem(
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
                        tint = Color.Unspecified,

                        )
                },
                label = { Text("profile") }
            )
        }
    }
}