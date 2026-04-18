package com.example.pawtracker.ui.components
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable


@Composable
fun NavBar() {
    NavigationBar {

        NavigationBarItem(
            selected = true,
            onClick = {},
            icon = { Icon(Icons.Filled.Home, contentDescription = "home") },
            label = { Text("home") }
        )

        NavigationBarItem(
            selected = false,
            onClick = {},
            icon = { Icon(Icons.Filled.Map, contentDescription = "map") },
            label = { Text("map") }
        )

        NavigationBarItem(
            selected = false,
            onClick = {},
            icon = { Icon(Icons.Filled.History, contentDescription = "history") },
            label = { Text("history") }
        )

        NavigationBarItem(
            selected = false,
            onClick = {},
            icon = { Icon(Icons.Filled.Person, contentDescription = "profile") },
            label = { Text("profile") }
        )
    }
}
