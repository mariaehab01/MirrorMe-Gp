package com.example.mirrorme.di


import com.example.mirrorme.data.repository.AuthRepositoryImpl
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.gotrue.gotrue
import com.example.mirrorme.data.source.AuthRemoteSource
import com.example.mirrorme.domain.repository.AuthRepository
import com.example.mirrorme.domain.usecase.SignUpUseCase
import io.github.jan.supabase.auth.Auth

object ServiceLocator {

    // 1. Create the Supabase client
    val supabaseClient = createSupabaseClient(
            supabaseUrl = "https://ebwuafypflcndjhamzuv.supabase.co",
            supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImVid3VhZnlwZmxjbmRqaGFtenV2Iiwicm9sZSI6ImFub24iLCJpYXQiOjE3NDk0MTkyMjEsImV4cCI6MjA2NDk5NTIyMX0.3jLJJ2UQaxZBRXpxhb7vnUzhjASDQCDEDFWnOXIMq_I"
    ) {
            install(Auth)
    }

    // 2. Create remote data source
    val authRemoteSource: AuthRemoteSource by lazy {
        AuthRemoteSource(supabaseClient)
    }

    // 3. Create repository
    val authRepository: AuthRepository by lazy {
        AuthRepositoryImpl(authRemoteSource)
    }

    // 4. Create use cases
    val signUpUseCase: SignUpUseCase by lazy {
        SignUpUseCase(authRepository)
    }

}
