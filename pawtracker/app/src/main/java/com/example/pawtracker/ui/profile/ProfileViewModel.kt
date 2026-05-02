package com.example.pawtracker.ui.profile
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pawtracker.data.repository.DogProfileRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class ProfileViewModel(
    private val repository: DogProfileRepository
) : ViewModel() {

    // Temporary state to hold user input before it is saved to the database
    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    init {
        loadInitialData()
    }

    // Fetches the existing profile once to fill the input fields when the screen opens
    private fun loadInitialData() {
        viewModelScope.launch {
            repository.getProfile().collect { dogProfile ->
                dogProfile?.let { data ->
                    _uiState.update { currentState ->
                        currentState.copy(
                            name = data.name,
                            breed = data.breed,
                            age = data.age,
                            height = data.height,
                            weight = data.weight,
                            dailyDistanceGoal = data.dailyDistanceGoal.toString(),
                            dailyDurationGoal = data.dailyDurationGoal.toString(),
                            imageUri = data.imageUri
                        )
                    }
                }
            }
        }
    }
}