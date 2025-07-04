package com.example.mirrorme.di

import GetProductByIdUseCase
import android.content.Context
import com.example.mirrorme.domain.usecase.SaveProfileUseCase
import com.example.mirrorme.data.repository.AuthRepositoryImpl
import com.example.mirrorme.data.repository.ProductRepositoryImpl
import com.example.mirrorme.data.repository.ProfileRepositoryImpl
import com.example.mirrorme.data.repository.MLRepositoryImpl
import com.example.mirrorme.data.session.SessionPreferences
import io.github.jan.supabase.createSupabaseClient
import com.example.mirrorme.data.source.AuthRemoteSource
import com.example.mirrorme.data.source.ProductRemoteSource
import com.example.mirrorme.data.source.ProfileRemoteSource
import com.example.mirrorme.data.source.ml.ApiService
import com.example.mirrorme.data.source.ml.CompatiblityRemoteSource
import com.example.mirrorme.data.source.ml.OutfitGRemoteSource
import com.example.mirrorme.data.source.ml.SimilarityRemoteSource
import com.example.mirrorme.domain.repository.AuthRepository
import com.example.mirrorme.domain.repository.ProfileRepository
import com.example.mirrorme.domain.repository.MLRepository
import com.example.mirrorme.domain.usecase.GetCompatibleUseCase
import com.example.mirrorme.domain.usecase.GetLastScreenUseCase
import com.example.mirrorme.domain.usecase.GetOutfitItemsUseCase
import com.example.mirrorme.domain.usecase.GetProductsUseCase
import com.example.mirrorme.domain.usecase.GetProfileUseCase
import com.example.mirrorme.domain.usecase.GetSimilarItemsUseCase
import com.example.mirrorme.domain.usecase.SetLastScreenUseCase
import com.example.mirrorme.domain.usecase.SignUpUseCase
import com.example.mirrorme.domain.usecase.SignInUseCase
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.postgrest.Postgrest
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.jvm.java

object ServiceLocator {

    // Supabase Client
    val supabaseClient = createSupabaseClient(
        supabaseUrl = "https://ebwuafypflcndjhamzuv.supabase.co",
        supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImVid3VhZnlwZmxjbmRqaGFtenV2Iiwicm9sZSI6ImFub24iLCJpYXQiOjE3NDk0MTkyMjEsImV4cCI6MjA2NDk5NTIyMX0.3jLJJ2UQaxZBRXpxhb7vnUzhjASDQCDEDFWnOXIMq_I"
    ) {
        install(Auth)
        install(Postgrest)
    }

    //API Client
    private val retrofit: Retrofit by lazy {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()

        Retrofit.Builder()
            .baseUrl("https://outfit-model-production.up.railway.app/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    //API Service
    val outfitApiService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }

    // --- Remote Sources ---
    val authRemoteSource: AuthRemoteSource by lazy {
        AuthRemoteSource(supabaseClient)
    }

    val profileRemoteSource by lazy {
        ProfileRemoteSource(supabaseClient)
    }

    val productRemoteSource by lazy {
        ProductRemoteSource(supabaseClient)
    }

    val similarityRemoteSource by lazy {
        SimilarityRemoteSource()
    }

    val compatibilityRemoteSource by lazy {
        CompatiblityRemoteSource()
    }

    val outfitGRemoteSource by lazy {
        OutfitGRemoteSource(outfitApiService)
    }

    // --- Repositories ---
    val authRepository: AuthRepository by lazy {
        AuthRepositoryImpl(authRemoteSource)
    }

    val profileRepository by lazy {
        ProfileRepositoryImpl(profileRemoteSource)
    }

    val productRepository by lazy {
        ProductRepositoryImpl(productRemoteSource)
    }

    val mlRepository: MLRepository by lazy {
        MLRepositoryImpl(similarityRemoteSource, compatibilityRemoteSource, outfitGRemoteSource)
    }

    // --- Use Cases ---
    val signUpUseCase: SignUpUseCase by lazy {
        SignUpUseCase(authRepository)
    }

    val signInUseCase: SignInUseCase by lazy {
        SignInUseCase(authRepository)
    }

    val saveProfileUseCase by lazy {
        SaveProfileUseCase(profileRepository)
    }

    val getProductsUseCase by lazy {
        GetProductsUseCase(productRepository)
    }

    val getProductByIdUseCase = GetProductByIdUseCase(productRepository)

    val getSimilarItemsUseCase by lazy {
        GetSimilarItemsUseCase(mlRepository)
    }

    val getComptibleItemsUseCase by lazy {
        GetCompatibleUseCase(mlRepository)
    }

    val getOutfitItemsUseCase by lazy {
        GetOutfitItemsUseCase(mlRepository)
    }

    // --- Session ---
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
