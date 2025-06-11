package com.example.mirrorme.di


import SaveProfileUseCase
import com.example.mirrorme.data.repository.AuthRepositoryImpl
import com.example.mirrorme.data.repository.ProfileRepositoryImpl
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import com.example.mirrorme.data.source.AuthRemoteSource
import com.example.mirrorme.data.source.ProfileRemoteSource
import com.example.mirrorme.domain.repository.AuthRepository
import com.example.mirrorme.domain.usecase.SignUpUseCase
import com.example.mirrorme.domain.usecase.SignInUseCase
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.postgrest.Postgrest

object ServiceLocator {

    // Create the Supabase client
    val supabaseClient = createSupabaseClient(
        supabaseUrl = "https://ebwuafypflcndjhamzuv.supabase.co",
        supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImVid3VhZnlwZmxjbmRqaGFtenV2Iiwicm9sZSI6ImFub24iLCJpYXQiOjE3NDk0MTkyMjEsImV4cCI6MjA2NDk5NTIyMX0.3jLJJ2UQaxZBRXpxhb7vnUzhjASDQCDEDFWnOXIMq_I"
    ) {
        install(Auth)
        install(Postgrest)
    }

    // Create remote data source
    val authRemoteSource: AuthRemoteSource by lazy {
        AuthRemoteSource(supabaseClient)
    }

    private val profileRemoteSource by lazy {
        ProfileRemoteSource(supabaseClient)
    }

    // Create repository
    val authRepository: AuthRepository by lazy {
        AuthRepositoryImpl(authRemoteSource)
    }

    private val profileRepository by lazy {
        ProfileRepositoryImpl(profileRemoteSource)
    }

    //  Create use cases
    val signUpUseCase: SignUpUseCase by lazy {
        SignUpUseCase(authRepository)
    }

    val signInUseCase: SignInUseCase by lazy {
        SignInUseCase(authRepository)
    }

    val saveProfileUseCase by lazy {
        SaveProfileUseCase(profileRepository)
    }


}