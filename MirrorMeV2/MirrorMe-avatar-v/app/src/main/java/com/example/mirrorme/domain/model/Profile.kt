package com.example.mirrorme.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Profile(
    val id: String, // This should be the logged-in user's UUID
    val phone: String,
    val height: Int,
    val weight: Int,
    val body_shape: String,
    val skin_tone: String,
    val gender: String
)