package com.example.pawtracker.ui.main

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pawtracker.data.local.preferences.PreferenceRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
/**
 * ViewModel for the onboarding screen shown on first app launch.
 * Reads whether onboarding is completed and saves the result when the user continues.
 */


class MainViewModel(private val repository: PreferenceRepository) : ViewModel() {
 private val _uiState = MutableStateFlow(MainUiState())
    val uiState: StateFlow<MainUiState> = _uiState

    var hasCompletedOnboarding by mutableStateOf<Boolean?>(null)
        private set

    init {
        viewModelScope.launch {
            repository.onboardingCompleted.collect { hasCompletedOnboarding = it }
        }
    }

    fun completeOnboarding() {
        viewModelScope.launch {
            repository.setOnboardingCompleted()
        }
    }
}
