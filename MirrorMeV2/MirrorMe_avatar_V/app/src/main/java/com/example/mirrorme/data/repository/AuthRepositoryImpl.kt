package com.example.mirrorme.data.repository

import com.example.mirrorme.domain.repository.AuthRepository
import com.example.mirrorme.data.source.AuthRemoteSource

class AuthRepositoryImpl(
    private val remote: AuthRemoteSource
) : AuthRepository {

    override suspend fun signUp(email: String, password: String) =
        remote.signUp(email, password)
    override suspend fun signIn(email: String, password: String) =
        remote.signIn(email, password)
    override suspend fun resetPassword(email: String) =
        remote.resetPassword(email)

}