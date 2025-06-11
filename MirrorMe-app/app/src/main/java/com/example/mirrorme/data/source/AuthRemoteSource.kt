package com.example.mirrorme.data.source

import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.builtin.Email

class AuthRemoteSource(private val supabaseClient: SupabaseClient) {

    suspend fun signUp(emailValue: String, passwordValue: String): Result<Unit> {
        return try {
            // Sign up
            val signUpResult = supabaseClient.auth.signUpWith(Email) {
                email = emailValue
                password = passwordValue
            }

            // Sign in (even if confirmation is required)
            val sessionResult = supabaseClient.auth.signInWith(Email) {
                email = emailValue
                password = passwordValue
            }

            val session = supabaseClient.auth.currentSessionOrNull()
            if (session != null) {
                Result.success(Unit)
            } else {
                Result.failure(Exception("Sign-in failed: session is null"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
