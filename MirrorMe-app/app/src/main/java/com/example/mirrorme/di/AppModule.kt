package com.example.mirrorme.di


import GetProductByIdUseCase
import SaveProfileUseCase
import android.content.Context
import com.example.mirrorme.data.repository.AuthRepositoryImpl
import com.example.mirrorme.data.repository.ProductRepositoryImpl
import com.example.mirrorme.data.repository.ProfileRepositoryImpl
import com.example.mirrorme.data.session.SessionPreferences
import io.github.jan.supabase.createSupabaseClient
import com.example.mirrorme.data.source.AuthRemoteSource
import com.example.mirrorme.data.source.ProductRemoteSource
import com.example.mirrorme.data.source.ProfileRemoteSource
import com.example.mirrorme.domain.repository.AuthRepository
import com.example.mirrorme.domain.repository.ProfileRepository
import com.example.mirrorme.domain.usecase.GetLastScreenUseCase
import com.example.mirrorme.domain.usecase.GetProductsUseCase
import com.example.mirrorme.domain.usecase.GetProfileUseCase
import com.example.mirrorme.domain.usecase.SetLastScreenUseCase
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

    val profileRemoteSource by lazy {
        ProfileRemoteSource(supabaseClient)
    }

    // Create repository
    val authRepository: AuthRepository by lazy {
        AuthRepositoryImpl(authRemoteSource)
    }

     val profileRepository by lazy {
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

    val productRemoteSource by lazy {
        ProductRemoteSource(supabaseClient)
    }

    val productRepository by lazy {
        ProductRepositoryImpl(productRemoteSource)
    }

    val getProductsUseCase by lazy {
        GetProductsUseCase(productRepository)
    }

    val getProductByIdUseCase = GetProductByIdUseCase(productRepository)


    // -- Session Persistence --
    private lateinit var sessionPreferences: SessionPreferences
    lateinit var setLastScreenUseCase: SetLastScreenUseCase
    lateinit var getLastScreenUseCase: GetLastScreenUseCase

    fun init(context: Context) {
        sessionPreferences = SessionPreferences(context)
        setLastScreenUseCase = SetLastScreenUseCase(sessionPreferences)
        getLastScreenUseCase = GetLastScreenUseCase(sessionPreferences)
    }

    fun provideGetProfileUseCase(repository: ProfileRepository): GetProfileUseCase {
        return GetProfileUseCase(repository)
    }
}