package com.example.mirrorme.presentation.cameraTryOn.utils

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.result.ActivityResultLauncher
import androidx.core.content.ContextCompat

/**
 * Checks if the camera permission is granted and starts the setup for Try-On UI.
 * If the permission is not granted, it requests the permission.
 */

fun checkCameraPermissionAndStart(
    context: Context,
    setupTryOnUI: () -> Unit,
    requestPermissionLauncher: ActivityResultLauncher<String>
) {
    when {
        ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED -> {
            setupTryOnUI()
        }
        // For Android 6.0 (API level 23) and above, we need to request permission
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.M -> {
            requestPermissionLauncher.launch(Manifest.permission.CAMERA)
        }
        else -> {
            setupTryOnUI()
        }
    }
}
