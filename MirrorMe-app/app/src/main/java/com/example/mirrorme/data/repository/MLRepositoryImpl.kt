package com.example.mirrorme.data.repository

import com.example.mirrorme.data.source.ml.CompatiblityRemoteSource
import com.example.mirrorme.data.source.ml.OutfitGRemoteSource
import com.example.mirrorme.data.source.ml.SimilarityRemoteSource
import com.example.mirrorme.domain.repository.MLRepository

class MLRepositoryImpl(
    private val similarityRemoteSource: SimilarityRemoteSource,
    private val compatibilityRemoteSource: CompatiblityRemoteSource,
    private val outfitRemoteSource: OutfitGRemoteSource

) : MLRepository {

    override suspend fun getSimilarItemIds(itemId: Int, topK: Int): List<Int> {
        return similarityRemoteSource.getSimilarItemIds(itemId, topK)
    }

    override suspend fun getCompatibleItemIds(itemId: Int, topK: Int): List<Int> {
        return compatibilityRemoteSource.getCompatibleItemIds(itemId, topK)
    }

    override suspend fun getOutfitItemIds(seedItemId: Int): Result<List<Int>> {
        return outfitRemoteSource.generateOutfit(seedItemId)
    }

}
