package com.example.mirrorme.data.repository

import com.example.mirrorme.data.source.ProductRemoteSource
import com.example.mirrorme.domain.model.Product
import com.example.mirrorme.domain.repository.ProductRepo

class ProductRepositoryImpl(
    private val remote: ProductRemoteSource
) : ProductRepo {
    override suspend fun getAllProducts(): Result<List<Product>> = remote.getAllProducts()
}