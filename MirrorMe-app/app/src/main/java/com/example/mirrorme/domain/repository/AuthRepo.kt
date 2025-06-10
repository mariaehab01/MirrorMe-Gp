package com.example.mirrorme.domain.repository

interface AuthRepository {
    suspend fun signUp(email: String, password: String): Result<Unit>
//    suspend fun signIn(email: String, password: String): Result<Unit>
//    suspend fun isUserSignedIn(): Boolean
}