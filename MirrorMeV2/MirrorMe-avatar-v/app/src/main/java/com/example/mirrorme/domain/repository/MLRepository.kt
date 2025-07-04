package com.example.mirrorme.domain.repository


interface MLRepository {
    suspend fun getSimilarItemIds(itemId: Int, topK: Int = 3): List<Int>
    suspend fun getCompatibleItemIds(itemId: Int, topK: Int = 3): List<Int>
    suspend fun getOutfitItemIds(seedItemId: Int): List<Int>
}
