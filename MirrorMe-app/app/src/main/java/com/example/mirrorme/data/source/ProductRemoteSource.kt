package com.example.mirrorme.data.source

import com.example.mirrorme.data.model.ProductDto
import com.example.mirrorme.domain.model.Product
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.from

class ProductRemoteSource(private val client: SupabaseClient) {
    suspend fun getAllProducts(): Result<List<Product>> {
        return try {
            val result = client
                .from("products")
                .select()
                .decodeList<ProductDto>()
                .map { it.toDomain() }
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}