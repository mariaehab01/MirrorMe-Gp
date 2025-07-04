package com.example.mirrorme.presentation.avatarTryOn

import android.annotation.SuppressLint
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView

/**
 * WebViewScreen.kt
 *
 * This file contains a composable function that displays a WebView for avatar try-on.
 * It loads a URL with parameters for height, weight
 * */

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun WebViewScreen(height: Int, weight: Int, gender: String, bodyShape: String, skinTone: String) {
    val url = "http://192.168.1.29:5173/?height=${height}&weight=${weight}&gender=${gender}&bodyShape=${bodyShape}&skinTone=${skinTone}"
    var isLoading by remember { mutableStateOf(true) }
    var webView: WebView? by remember { mutableStateOf(null) }

    Box(modifier = Modifier.fillMaxSize()) {
        AndroidView(factory = { context ->
            WebView(context).apply {
                layoutParams = android.view.ViewGroup.LayoutParams(
                    android.view.ViewGroup.LayoutParams.MATCH_PARENT,
                    android.view.ViewGroup.LayoutParams.MATCH_PARENT
                )

                webView = this
                settings.javaScriptEnabled = true
                settings.domStorageEnabled = true
                webViewClient = object : WebViewClient() {
                    override fun onPageFinished(view: WebView?, url: String?) {
                        super.onPageFinished(view, url)
                        isLoading = false
                    }
                }
                loadUrl(url)
            }
        }, modifier = Modifier.fillMaxSize())

        if (isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            webView?.destroy()
        }
    }
}
