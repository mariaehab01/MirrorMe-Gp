package com.example.mirrorme.data.repository

import com.example.mirrorme.data.source.ProductRemoteSource
import com.example.mirrorme.domain.model.Product
import com.example.mirrorme.domain.repository.ProductRepository

class ProductRepositoryImpl(
    private val remote: ProductRemoteSource
) : ProductRepository {
    override suspend fun getAllProducts(): Result<List<Product>> =
        remote.getAllProducts()
    override suspend fun getProductById(productId: String)=
         remote.getProductById(productId)

}