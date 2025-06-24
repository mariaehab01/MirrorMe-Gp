package com.example.mirrorme.data.session

import android.content.Context
import android.content.SharedPreferences

class SessionPreferences(context: Context) {
    private val prefs: SharedPreferences =
        context.getSharedPreferences("MirrorMePrefs", Context.MODE_PRIVATE)

    companion object {
        private const val KEY_LAST_SCREEN = "last_screen"
    }

    fun saveLastScreen(screen: String) {
        prefs.edit().putString(KEY_LAST_SCREEN, screen).apply()
    }

    fun getLastScreen(): String {
        return prefs.getString(KEY_LAST_SCREEN, "none") ?: "none"
    }
}