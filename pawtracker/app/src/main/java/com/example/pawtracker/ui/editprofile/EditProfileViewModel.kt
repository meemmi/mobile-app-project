package com.example.pawtracker.ui.editprofile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pawtracker.data.local.DogProfileEntity
import com.example.pawtracker.data.repository.DogProfileRepository
import com.example.pawtracker.ui.profile.ProfileUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class EditProfileViewModel(
    private val repository: DogProfileRepository
) : ViewModel() {

    // Constant stream of profile data from the database to keep the UI screens synced
    val dogProfile = repository.getProfile()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    // Temporary state to hold user input before it is saved to the database
    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    init {
        loadInitialData()
    }

    // Fetches the existing profile once to fill the input fields when the screen opens
    private fun loadInitialData() {
        viewModelScope.launch {

            val currentProfile = repository.getProfile().filterNotNull().firstOrNull()

            currentProfile?.let { dogProfile ->
                _uiState.update {
                    it.copy(
                        name = dogProfile.name,
                        breed = dogProfile.breed,
                        dailyDistanceGoal = dogProfile.dailyDistanceGoal.toString(),
                        dailyDurationGoal = dogProfile.dailyDurationGoal.toString(),
                        weeklyDistanceGoal = dogProfile.weeklyDistanceGoal.toString(),
                        weeklyDurationGoal = dogProfile.weeklyDurationGoal.toString(),
                        imageUri = dogProfile.imageUri
                    )
                }
            }
        }
    }

    // Updates functions to sync text field changes with the temporary UI state
    fun onNameChange(value: String) = _uiState.update { it.copy(name = value) }
    fun onBreedChange(value: String) = _uiState.update { it.copy(breed = value) }
    fun onDailyDistanceChange(value: String) = _uiState.update { it.copy(dailyDistanceGoal = value) }
    fun onDailyDurationChange(value: String) = _uiState.update { it.copy(dailyDurationGoal = value) }
    fun onWeeklyDistanceChange(value: String) = _uiState.update { it.copy(weeklyDistanceGoal = value) }
    fun onWeeklyDurationChange(value: String) = _uiState.update { it.copy(weeklyDurationGoal = value) }


    // Converts the temporary state back to an entity and saves it to the database
    fun save() {
        viewModelScope.launch {
            val state = _uiState.value

            val entity = DogProfileEntity(
                id = 0,
                imageUri = "testImage",
                name = state.name,
                breed = state.breed,
                // Converts String inputs to correct numeric types defaulting to 0 if invalid
                dailyDistanceGoal = state.dailyDistanceGoal.toFloatOrNull() ?: 0.0f,
                dailyDurationGoal = state.dailyDurationGoal.toLongOrNull() ?: 0L,
                weeklyDistanceGoal = state.weeklyDistanceGoal.toFloatOrNull() ?: 0.0f,
                weeklyDurationGoal = state.weeklyDurationGoal.toLongOrNull() ?: 0L
            )

            repository.saveProfile(entity)
        }
    }
}