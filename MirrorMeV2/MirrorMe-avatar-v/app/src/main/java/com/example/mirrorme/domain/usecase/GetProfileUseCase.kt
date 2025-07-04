package com.example.mirrorme.domain.usecase

import com.example.mirrorme.domain.model.Profile
import com.example.mirrorme.domain.repository.ProfileRepository

class GetProfileUseCase(
    private val repository: ProfileRepository
) {
    suspend operator fun invoke(): Result<Profile?> {
        return repository.getProfile()
    }
}
