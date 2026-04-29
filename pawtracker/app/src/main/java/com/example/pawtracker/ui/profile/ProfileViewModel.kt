package com.example.pawtracker.ui.profile
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pawtracker.data.local.DogProfileEntity
import com.example.pawtracker.data.repository.DogProfileRepository
import com.example.pawtracker.data.repository.DogProfileRepositoryImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


open class ProfileViewModel(
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

            val initialProfile = repository.getProfile().filterNotNull().firstOrNull()

            initialProfile?.let { dogProfile ->
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

}