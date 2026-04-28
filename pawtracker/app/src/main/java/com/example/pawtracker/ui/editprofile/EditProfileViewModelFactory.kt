package com.example.pawtracker.ui.editprofile

import com.example.pawtracker.data.repository.DogProfileRepositoryImpl
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModel

class EditProfileViewModelFactory(private val repo: DogProfileRepositoryImpl) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T = EditProfileViewModel(repo) as T
}