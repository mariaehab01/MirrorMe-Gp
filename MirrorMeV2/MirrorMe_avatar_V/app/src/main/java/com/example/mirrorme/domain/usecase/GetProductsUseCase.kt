package com.example.mirrorme.domain.usecase

import com.example.mirrorme.domain.model.Product
import com.example.mirrorme.domain.repository.ProductRepository

class GetProductsUseCase(
    private val repository: ProductRepository
) {
    suspend operator fun invoke(): Result<List<Product>> = repository.getAllProducts()
}