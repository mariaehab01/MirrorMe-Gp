package com.example.mirrorme.domain.usecase

import com.example.mirrorme.domain.repository.ProfileRepository

class SaveProfileUseCase(private val profileRepository: ProfileRepository) {
    suspend operator fun invoke(
        phone: String,
        height: Int,
        weight: Int,
        bodyShape: String,
        skinTone: String,
        gender: String
    ) = profileRepository.saveProfile(
       phone, height, weight, bodyShape, skinTone, gender
    )
}