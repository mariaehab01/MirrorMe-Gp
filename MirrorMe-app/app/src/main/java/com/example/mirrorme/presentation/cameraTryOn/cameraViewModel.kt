package com.example.mirrorme.presentation.cameraTryOn

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mirrorme.domain.model.Profile
import com.example.mirrorme.di.ServiceLocator
import kotlinx.coroutines.launch
import androidx.compose.runtime.*

class View3DViewModel : ViewModel() {

    private val profileRepository = ServiceLocator.profileRepository

    var profileUiState by mutableStateOf<Profile?>(null)
        private set

    var isLoading by mutableStateOf(false)
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    fun loadProfile() {
        viewModelScope.launch {
            isLoading = true
            val result = profileRepository.getProfile()
            result.onSuccess { profile ->
                profileUiState = profile
            }.onFailure { error ->
                errorMessage = error.message
            }
            isLoading = false
        }
    }
}
