package com.example.mirrorme.domain.model

data class Product(
    val id: String,
    val name: String,
    val price: Double,
    val imageUrl: String,
    val category: String,
    val gender: String,
    val objectUrl: String
)

