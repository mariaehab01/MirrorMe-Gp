package com.example.mirrorme.domain.usecase

import com.example.mirrorme.domain.repository.MLRepository

class GetCompatibleUseCase (private val mlRepository: MLRepository){
    suspend operator fun invoke(itemId: Int, topK: Int = 3): List<Int> {
        return mlRepository.getCompatibleItemIds(itemId, topK)
    }
}