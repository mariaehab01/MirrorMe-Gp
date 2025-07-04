package com.example.mirrorme.domain.usecase

import com.example.mirrorme.domain.repository.AuthRepository

class SignUpUseCase(private val authRepository: AuthRepository) {
    suspend operator fun invoke(email: String, password: String): Result<Unit> {
        return authRepository.signUp(email, password)
    }
}