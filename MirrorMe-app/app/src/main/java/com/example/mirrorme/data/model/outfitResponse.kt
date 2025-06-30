package com.example.mirrorme.data.model

data class OutfitResponse(
    val outfit: OutfitItems
)

data class OutfitItems(
    val item_count: Int,
    val item: List<OutfitItem>
)

data class OutfitItem(
    val id: Int
)
