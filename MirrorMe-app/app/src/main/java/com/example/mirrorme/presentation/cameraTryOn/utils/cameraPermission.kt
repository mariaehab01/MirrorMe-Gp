package com.example.mirrorme.presentation.cameraTryOn.utils

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.result.ActivityResultLauncher
import androidx.core.content.ContextCompat

fun checkCameraPermissionAndStart(
    context: Context,
    setupTryOnUI: () -> Unit,
    requestPermissionLauncher: ActivityResultLauncher<String>
) {
    when {
        ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED -> {
            setupTryOnUI()
        }
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.M -> {
            requestPermissionLauncher.launch(Manifest.permission.CAMERA)
        }
        else -> {
            setupTryOnUI()
        }
    }
}
