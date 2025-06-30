package com.example.mirrorme.data.source

import android.util.Log
import com.example.mirrorme.domain.model.Profile
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.postgrest
import kotlinx.serialization.Serializable

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
    suspend fun getProfile(): Result<Profile?> {
        val userId = supabaseClient.auth.currentUserOrNull()?.id
            ?: return Result.failure(Exception("User not authenticated"))

        return try {
            val profile = supabaseClient
                .from("profiles")
                .select {
                    filter { eq("id", userId) }
                    single()
                }
                .decodeAs<Profile>()
            Log.d("profile", "Fetched product: $profile")

            Result.success(profile)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }


}
