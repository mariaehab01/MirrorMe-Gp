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
            val pageSize = 1000
            var from = 0
            var to = pageSize - 1
            val allProducts = mutableListOf<Product>()

            while (true) {
                val result = client
                    .from("products")
                    .select {
                        range(from.toLong(), to.toLong())
                    }
                    .decodeList<ProductDto>()
                    .map { it.toDomain() }

                if (result.isEmpty()) break

                allProducts.addAll(result)
                Log.d("ProductRemoteSource", "Fetched ${result.size} products from $from to $to")

                from += pageSize
                to += pageSize
            }

            Result.success(allProducts)
        } catch (e: Exception) {
            Log.e("ProductRemoteSource", "Failed to fetch products: ${e.message}", e)
            Result.failure(e)
        }
    }

    suspend fun getProductById(productId: Int): Result<Product> {
        return try {
            val result = client
                .from("products")
                .select {
                    filter {
                        eq("ml_id", productId)
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