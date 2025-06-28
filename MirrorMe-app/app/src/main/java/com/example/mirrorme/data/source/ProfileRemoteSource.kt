package com.example.mirrorme.data.source

import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.postgrest.postgrest
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


class ProfileRemoteSource(private val supabaseClient: SupabaseClient) {

    suspend fun saveProfile(
        phone: String,
        height: Int,
        weight: Int,
        bodyShape: String,
        skinTone: String,
        gender: String
    ): Result<Unit> {
        return try {
            val userId = supabaseClient.auth.currentUserOrNull()?.id
                ?: return Result.failure(Exception("User not authenticated"))

            val profile = Profile(
                id = userId,
                phone = phone,
                height = height,
                weight = weight,
                body_shape = bodyShape,
                skin_tone = skinTone,
                gender = gender
            )

            supabaseClient.postgrest["profiles"].insert(profile)
            Result.success(Unit)
        } catch (e: Exception) {
            val errorMessage = e.message.orEmpty().lowercase()

            return if (errorMessage.contains("duplicate key") || errorMessage.contains("already exists")) {
                Result.failure(Exception("Profile already exists for this user."))
            } else {
                Result.failure(e)
            }

        }
    }
}
