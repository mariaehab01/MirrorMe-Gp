package com.example.mirrorme.data.repository

import com.example.mirrorme.data.source.ProfileRemoteSource
import com.example.mirrorme.domain.model.Profile
import com.example.mirrorme.domain.repository.ProfileRepository

class ProfileRepositoryImpl(
    private val remote: ProfileRemoteSource
) : ProfileRepository {
    override suspend fun saveProfile(
        phone: String,
        height: Int,
        weight: Int,
        bodyShape: String,
        skinTone: String,
        gender: String
    ) = remote.saveProfile(phone, height, weight, bodyShape, skinTone, gender)
    override suspend fun getProfile(): Result<Profile?> {
        return remote.getProfile()
    }
}
