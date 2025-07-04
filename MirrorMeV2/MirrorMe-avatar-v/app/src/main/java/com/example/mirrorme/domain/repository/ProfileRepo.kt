package com.example.mirrorme.domain.repository

import com.example.mirrorme.domain.model.Profile

interface ProfileRepository {
    suspend fun saveProfile(
        phone: String,
        height: Int,
        weight: Int,
        bodyShape: String,
        skinTone: String,
        gender: String
    ): Result<Unit>
    suspend fun getProfile(): Result<Profile?>
}
