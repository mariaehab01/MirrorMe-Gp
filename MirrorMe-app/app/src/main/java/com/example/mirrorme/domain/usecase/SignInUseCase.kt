package com.example.mirrorme.domain.usecase

import com.example.mirrorme.domain.repository.AuthRepository

class SignInUseCase(private val repository: AuthRepository) {
    suspend operator fun invoke(email: String, password: String): Result<Unit> {
        return repository.signIn(email, password)
    }
}