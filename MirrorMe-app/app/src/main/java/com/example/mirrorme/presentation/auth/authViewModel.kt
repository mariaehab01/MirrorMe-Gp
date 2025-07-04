package com.example.mirrorme.presentation.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import com.example.mirrorme.di.ServiceLocator
import com.example.mirrorme.domain.usecase.SignUpUseCase
import com.example.mirrorme.domain.usecase.SignInUseCase

sealed class AuthUiState {
    object Idle : AuthUiState()
    object Loading : AuthUiState()
    object Success : AuthUiState()
    data class Error(val message: String) : AuthUiState()
}

class AuthViewModel : ViewModel() {
    private val signUpUseCase: SignUpUseCase = ServiceLocator.signUpUseCase
    private val signInUseCase: SignInUseCase = ServiceLocator.signInUseCase
    private val saveProfileUseCase = ServiceLocator.saveProfileUseCase

    private val _uiState = MutableStateFlow<AuthUiState>(AuthUiState.Idle)
    val uiState: StateFlow<AuthUiState> = _uiState

    fun signUp(email: String, password: String) {
        viewModelScope.launch {
            _uiState.value = AuthUiState.Loading
            val result = signUpUseCase(email, password)
            _uiState.value = if (result.isSuccess) {
                AuthUiState.Success
            } else {
                AuthUiState.Error(result.exceptionOrNull()?.message ?: "Unknown error")
            }
        }
    }



    fun signIn(email: String, password: String) {
        viewModelScope.launch {
            _uiState.value = AuthUiState.Loading
            val result = signInUseCase(email, password)
            _uiState.value = if (result.isSuccess) {
                AuthUiState.Success
            } else {
                AuthUiState.Error(result.exceptionOrNull()?.message ?: "Sign-in failed")
            }
        }
    }

    fun saveProfile(
        phone: String,
        height: Int,
        weight: Int,
        bodyShape: String,
        skinTone: String,
        gender: String
    ) {
        viewModelScope.launch {
            _uiState.value = AuthUiState.Loading

            val result = saveProfileUseCase(
                phone = phone,
                height = height.toInt(),
                weight = weight.toInt(),
                bodyShape = bodyShape,
                skinTone = skinTone,
                gender = gender
            )

            _uiState.value = if (result.isSuccess) {
                AuthUiState.Success
            } else {
                AuthUiState.Error(result.exceptionOrNull()?.message ?: "Unknown error")
            }
        }
    }



}