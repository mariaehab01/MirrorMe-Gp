package com.example.mirrorme.data.model

import com.example.mirrorme.domain.model.Product
import kotlinx.serialization.Serializable

@Serializable
data class ProductDto(
    val id: String,
    val name: String,
    val price: Double,
    val image_url: String,
    val category: String,
    val gender: String,
    val object_url: String
) {
    fun toDomain(): Product {
        return Product(
            id = id,
            name = name,
            price = price,
            imageUrl = image_url,
            category = category,
            gender = gender,
            objectUrl = object_url
        )
    }
}