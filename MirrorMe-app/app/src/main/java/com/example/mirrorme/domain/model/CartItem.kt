package com.example.mirrorme.domain.model

data class CartItem(
    val productId: Int,
    val name: String,
    val imageRes: String,
    val color: String,
    val size: String,
    val price: Double,
    var quantity: Int
)