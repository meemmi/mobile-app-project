package com.example.pawtracker.ui.main

import androidx.compose.runtime.Composable



@Composable
fun MainScreen(
    uiState: MainUiState,
    onContinueClick: () -> Unit
) {
    // your layout with big dog background etc.
    Button(onClick = onContinueClick) {
        Text("Continue")
    }
}
@Composable
fun MainRoute(
    viewModel: MainViewModel = hiltViewModel(), // or your DI
    onContinueClick: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    MainScreen(
        uiState = uiState,
        onContinueClick = onContinueClick
    )
}


