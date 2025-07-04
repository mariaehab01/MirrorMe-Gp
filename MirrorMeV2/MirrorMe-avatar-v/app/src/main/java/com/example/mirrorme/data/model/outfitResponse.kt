package com.example.mirrorme.data.model

data class OutfitResponse(
    val outfit: OutfitGroup?
)

data class OutfitGroup(
    val item_count: Int,
    val items: List<OutfitItem>
)

data class OutfitItem(
    val id: Int
)
