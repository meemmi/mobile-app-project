package com.example.pawtracker.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector

data class NavBarItem(
    val destination: Screens,
    val icon: ImageVector,
    val label: String
)

@Composable
fun NavBar(navController: NavHostController
) {
    val items = listOf(
        NavBarItem(Screens.Main, Icons.Default.Home, "home"),
        NavBarItem(Screens.Tracking, Icons.Default.Map, "map"),
        NavBarItem(Screens.History, Icons.Default.History, "history"),
        NavBarItem(Screens.Profile, Icons.Default.Person, "profile"),
    )

    NavigationBar {
        val currentDestination = navController.currentBackStackEntryAsState().value?.destination?.route

        items.forEach { item ->
            NavigationBarItem(
                selected = currentDestination == item.destination.route,
                onClick = {
                    navController.navigate(item.destination.route) {
                        popUpTo(AppDestination.Main.route) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = { Icon(item.icon, contentDescription = item.label) },
                label = { Text(item.label) }
            )
        }
    }
    @Composable
    fun AppNavHost(
        navController: NavHostController
    ) {
        Scaffold(
            bottomBar = { BottomNavBar(navController) }
        ) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = AppDestination.Main.route,
                modifier = Modifier.padding(innerPadding)
            ) {
                // composable(...) as before
            }
        }
    }

}
