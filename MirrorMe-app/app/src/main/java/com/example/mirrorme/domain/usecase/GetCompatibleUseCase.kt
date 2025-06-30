package com.example.mirrorme.domain.usecase

import com.example.mirrorme.di.ServiceLocator.mlRepository

class GetCompatibleUseCase {
    suspend operator fun invoke(itemId: Int, topK: Int = 3): List<Int> {
        return mlRepository.getCompatibleItemIds(itemId, topK)
    }
}