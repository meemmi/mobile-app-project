package com.example.pawtracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.pawtracker.ui.components.NavBar
import com.example.pawtracker.ui.components.NavigationRail
import com.example.pawtracker.ui.main.MainViewModel
import com.example.pawtracker.ui.navigation.NavGraph
import com.example.pawtracker.ui.navigation.NavigationType
import com.example.pawtracker.ui.theme.PawTrackerTheme
import com.example.pawtracker.utils.ViewModelFactory


class MainActivity : ComponentActivity() {

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
        }

    private fun requestLocationPermission() {
        requestPermissionLauncher.launch(android.Manifest.permission.ACCESS_FINE_LOCATION)
    }

    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val app = application as PawTrackerApplication

        requestLocationPermission()
        enableEdgeToEdge()

        setContent {
            val windowSize = calculateWindowSizeClass(this)

            val navigationType = when (windowSize.widthSizeClass) {
                WindowWidthSizeClass.Compact -> NavigationType.BOTTOM_NAVIGATION
                WindowWidthSizeClass.Medium -> NavigationType.NAVIGATION_RAIL
                WindowWidthSizeClass.Expanded -> NavigationType.PERMANENT_NAVIGATION_DRAWER
                else -> NavigationType.BOTTOM_NAVIGATION
            }

            val mainViewModel: MainViewModel = viewModel(
                factory = ViewModelFactory { MainViewModel(app.preferenceRepository) }
            )

            val systemDark = isSystemInDarkTheme()
            var isDarkTheme by rememberSaveable { mutableStateOf(systemDark) }

            PawTrackerTheme(darkTheme = isDarkTheme) {
                val navController = rememberNavController()

                Row(modifier = Modifier.fillMaxSize()) {

                    if (navigationType != NavigationType.BOTTOM_NAVIGATION) {
                        NavigationRail(
                            navController = navController,
                            navigationType = navigationType,
                            hasCompletedOnboarding = mainViewModel.hasCompletedOnboarding ?: false
                        )
                    }

                    Scaffold(
                        bottomBar = {

                            if (navigationType == NavigationType.BOTTOM_NAVIGATION) {
                                NavBar(
                                    navController,
                                    has_completed_onboarding = mainViewModel.hasCompletedOnboarding
                                        ?: false
                                )
                            }
                        }
                    ) { innerPadding ->
                        NavGraph(
                            navController = navController,
                            isDarkTheme = isDarkTheme,
                            onToggleTheme = { isDarkTheme = !isDarkTheme },
                            innerPadding = innerPadding,
                            viewModel = mainViewModel,
                            app = app,
                            navigationType = navigationType
                        )
                    }
                }
            }
        }

    }
    @Preview(showBackground = true)
    @Composable
    fun GreetingPreview() {
        PawTrackerTheme {

        }
    }
}