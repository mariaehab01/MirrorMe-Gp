package com.example.mirrorme.domain.repository

interface AuthRepository {
    suspend fun signUp(email: String, password: String): Result<Unit>
    suspend fun signIn(emailValue: String, passwordValue: String): Result<Unit>
    suspend fun resetPassword(emailValue: String): Result<Unit>
}