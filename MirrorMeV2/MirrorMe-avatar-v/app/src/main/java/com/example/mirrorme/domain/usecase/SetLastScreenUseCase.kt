package com.example.mirrorme.domain.usecase

import com.example.mirrorme.data.session.SessionPreferences

class SetLastScreenUseCase(private val sessionPrefs: SessionPreferences) {
    operator fun invoke(screen: String) {
        sessionPrefs.saveLastScreen(screen)
    }
}