package com.example.mirrorme.domain.usecase

import com.example.mirrorme.data.session.SessionPreferences

class GetLastScreenUseCase(private val sessionPrefs: SessionPreferences) {
    operator fun invoke(): String {
        return sessionPrefs.getLastScreen()
    }
}