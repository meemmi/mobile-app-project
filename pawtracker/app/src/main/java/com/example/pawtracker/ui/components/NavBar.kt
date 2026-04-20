package com.example.pawtracker.ui.components
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
//import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Place

import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Pets
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.pawtracker.R
import com.example.pawtracker.ui.history.HistoryScreen
import com.example.pawtracker.ui.navigation.Screen


@Composable
fun NavBar(navController: NavHostController) {
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
    Box(
        modifier = Modifier.height(55.dp)   // ← make it smaller (default is 80dp)
    ) {
        NavigationBar(
            modifier = Modifier.fillMaxSize()
        ) {

            NavigationBarItem(
                selected = true,
                onClick = {navController.navigate(Screen.Main.route)},
                icon = { Icon(Icons.Filled.Home, contentDescription = "home") },
                label = { Text("home") }
            )

            NavigationBarItem(
                selected = false,
                onClick = {navController.navigate(Screen.Tracking.route)},
                icon = { Icon(Icons.Filled.Place, contentDescription = "map") },
                label = { Text("map") }
            )

            NavigationBarItem(
                selected = false,
                onClick = {navController.navigate(Screen.History.route)},
                icon = { Icon(Icons.Filled.History, contentDescription = "history") },
                label = { Text("history") }
            )

            NavigationBarItem(
                selected = false,
                onClick = {navController.navigate(Screen.Profile.route)},
                icon = {
                    Icon(
                        painter = painterResource(id = R.drawable.dog_icon),
                        contentDescription = "profile"
                    )
                },
                label = { Text("profile") }
            )
        }
    }
}

