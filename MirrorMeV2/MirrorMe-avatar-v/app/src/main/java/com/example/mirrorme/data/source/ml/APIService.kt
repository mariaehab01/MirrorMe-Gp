package com.example.mirrorme.data.source.ml

import com.example.mirrorme.data.model.OutfitResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    data class OutfitRequest(val seed_item_id: Int)

    @POST("api/v1/outfit/generate")
    suspend fun generateOutfit(@Body body: OutfitRequest): OutfitResponse
}
