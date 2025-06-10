package com.example.mirrorme.domain.repository

interface ProfileRepository {
    suspend fun saveProfile(
        phone: String,
        height: Int,
        weight: Int,
        bodyShape: String,
        skinTone: String,
        gender: String
    ): Result<Unit>
}
