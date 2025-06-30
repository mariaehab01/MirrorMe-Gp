package com.example.mirrorme.domain.usecase

import com.example.mirrorme.domain.repository.MLRepository


class GetOutfitItemsUseCase(private val mlRepository: MLRepository) {
    suspend operator fun invoke(seedItemId: Int): Result<List<Int>> {
        return mlRepository.getOutfitItemIds(seedItemId)
    }
}
