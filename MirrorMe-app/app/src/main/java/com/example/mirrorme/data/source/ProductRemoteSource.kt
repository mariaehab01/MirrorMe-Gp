package com.example.mirrorme.data.source

import android.util.Log
import com.example.mirrorme.data.model.ProductDto
import com.example.mirrorme.domain.model.Product
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.from
import kotlin.collections.map

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

    suspend fun getProductById(productId: String): Result<Product> {
        return try {
            val result = client
                .from("products")
                .select {
                    filter {
                        eq("id", productId)
                    }
                    single()
                }
                .decodeAs<ProductDto>()
                .toDomain()

            Log.d("ProductRemoteSource", "Fetched product: $result")
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }



}