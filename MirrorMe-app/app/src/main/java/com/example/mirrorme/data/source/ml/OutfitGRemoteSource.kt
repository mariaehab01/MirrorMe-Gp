package com.example.mirrorme.data.source.ml

import android.util.Log
import com.example.mirrorme.data.source.ml.ApiService.OutfitRequest

class OutfitGRemoteSource(private val apiService: ApiService) {
    suspend fun generateOutfit(seedItemId: Int): List<Int> {
        return try {
            val response = apiService.generateOutfit(OutfitRequest(seedItemId))
            val outfitIds = response.outfit?.items?.map { it.id } ?: emptyList()
            Log.d("OutfitRemoteSource", "Outfit IDs: $outfitIds")
            outfitIds
        } catch (e: Exception) {
            Log.e("OutfitRemoteSource", "Error: ${e.localizedMessage}", e)
            emptyList()
        }
    }
}
