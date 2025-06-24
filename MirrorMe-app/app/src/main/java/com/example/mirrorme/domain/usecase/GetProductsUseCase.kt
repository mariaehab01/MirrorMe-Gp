package com.example.mirrorme.domain.usecase

import com.example.mirrorme.domain.model.Product
import com.example.mirrorme.domain.repository.ProductRepo

class GetProductsUseCase(
    private val repository: ProductRepo
) {
    suspend operator fun invoke(): Result<List<Product>> = repository.getAllProducts()
}