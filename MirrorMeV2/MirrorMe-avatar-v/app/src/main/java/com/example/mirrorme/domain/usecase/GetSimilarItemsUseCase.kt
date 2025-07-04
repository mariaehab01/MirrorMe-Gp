package com.example.mirrorme.domain.usecase

import com.example.mirrorme.domain.repository.MLRepository

class GetSimilarItemsUseCase(private val mlRepository: MLRepository) {
    suspend operator fun invoke(itemId: Int, topK: Int = 3): List<Int> {
        return mlRepository.getSimilarItemIds(itemId, topK)
    }
}
