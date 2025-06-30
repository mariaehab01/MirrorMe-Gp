package com.example.mirrorme.data.source.ml

import android.util.Log


class OutfitGRemoteSource (private val apiService: ApiService) {
    suspend fun generateOutfit(seedItemId: Int): Result<List<Int>> {
        return try {
            val response = apiService.generateOutfit(mapOf("seed_item_id" to seedItemId))
            val outfitIds = response.outfit.item.map { it.id }
            Log.d("OutfitRemoteSource", "Outfit IDs: $outfitIds")

            Result.success(outfitIds)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
