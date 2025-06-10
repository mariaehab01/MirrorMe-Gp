package com.example.mirrorme.data.source

import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.builtin.Email

class AuthRemoteSource(private val supabaseClient: SupabaseClient) {

    suspend fun signUp(emailValue: String, passwordValue: String): Result<Unit> {
        return try {
            val response = supabaseClient.auth.signUpWith(Email){
                email = emailValue
                password = passwordValue
            }
            if (response != null) {
                Result.success(Unit)
            } else {
                Result.failure(Exception("Sign-up failed: no user session"))
            }

        } catch (e: Exception) {
            Result.failure(e)
        }
    }

//    suspend fun signIn(email: String, password: String): Result<Unit> {
//    }
//
//    suspend fun isUserSignedIn(): Boolean {
//    }
}