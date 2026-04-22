package com.example.pawtracker.ui.components

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Place

import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Pets
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.pawtracker.R
import com.example.pawtracker.ui.history.HistoryScreen
import com.example.pawtracker.ui.navigation.Screen



@Composable
fun NavBar(navController: NavHostController) {
    val currentRoute =
        navController.currentBackStackEntryAsState().value?.destination?.route

    NavigationBar(
        modifier = Modifier.height(64.dp)
    ) {

        NavigationBarItem(
            selected = currentRoute == Screen.Main.route,
            onClick = {
                navController.navigate(Screen.Main.route) {
                    popUpTo(navController.graph.startDestinationId) { saveState = true }
                    launchSingleTop = true
                    restoreState = true
                }
            },
            icon = { Icon(Icons.Filled.Home, contentDescription = "home",
                    modifier = Modifier
                        .size(24.dp)
                        .padding(top = 4.dp)) },
            label = { Text("home") }
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
            icon = { Icon(Icons.Filled.Place, contentDescription = "map",
                        modifier = Modifier
                       .size(24.dp)
                      .padding(top = 4.dp)) },
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
            icon = { Icon(Icons.Filled.History, contentDescription = "history",
                modifier = Modifier
                    .size(24.dp)
                    .padding(top = 4.dp)) },
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
                    modifier = Modifier.size(24.dp).padding(top = 4.dp),
                    tint = Color.Unspecified,

                )
            },
            label = { Text("profile") }
        )
    }
}