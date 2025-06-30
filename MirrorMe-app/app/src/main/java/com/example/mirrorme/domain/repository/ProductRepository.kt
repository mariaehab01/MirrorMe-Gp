package com.example.mirrorme.domain.repository

import com.example.mirrorme.domain.model.Product

interface ProductRepository {
    suspend fun getAllProducts(): Result<List<Product>>
    suspend fun getProductById(productId: Int): Result<Product>
}