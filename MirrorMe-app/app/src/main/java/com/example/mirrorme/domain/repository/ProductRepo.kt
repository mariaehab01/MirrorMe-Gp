package com.example.mirrorme.domain.repository

import com.example.mirrorme.domain.model.Product

interface ProductRepo {
    suspend fun getAllProducts(): Result<List<Product>>
}