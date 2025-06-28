//without the overlay
package com.example.mirrorme.data.tryOn

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.View
import android.widget.FrameLayout
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import com.google.mediapipe.tasks.vision.core.RunningMode
import io.github.sceneview.SceneView
import io.github.sceneview.node.ModelNode
import java.util.concurrent.Executors


fun createAccurateCameraWithPoseAnd3D(
    context: Context,
    lifecycleOwner: LifecycleOwner,
    onPoseResult: (PoseLandmarkerHelper.ResultBundle) -> Unit,
    onModelReady: (ModelNode) -> Unit,
): View {
    val container = FrameLayout(context)

    val previewView = PreviewView(context).apply {
        scaleType = PreviewView.ScaleType.FIT_CENTER
        layoutParams = FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.MATCH_PARENT,
            FrameLayout.LayoutParams.MATCH_PARENT
        )
    }
    container.addView(previewView)

    val sceneView = SceneView(
        context = context,
        isOpaque = false,
        cameraManipulator = null,
    ).apply {
        layoutParams = FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.MATCH_PARENT,
            FrameLayout.LayoutParams.MATCH_PARENT
        )
        setBackgroundColor(Color.TRANSPARENT)
    }
    container.addView(sceneView)

    // Load the 3D clothing model
    loadModelIntoScene(
        context = context,
        sceneView = sceneView,
        modelPath = "models/tshirt.glb", // Change as needed
        onModelReady = onModelReady
    )

    // Pose detection helper
    val poseLandmarkerHelper = PoseLandmarkerHelper(
        runningMode = RunningMode.LIVE_STREAM,
        context = context,
        poseLandmarkerHelperListener = object : PoseLandmarkerHelper.LandmarkerListener {
            override fun onResults(resultBundle: PoseLandmarkerHelper.ResultBundle) {
                onPoseResult(resultBundle)
            }

            override fun onError(error: String, errorCode: Int) {
                Log.e("PoseDetection", "PoseLandmarker error: $error")
            }
        }
    )

    val executor = Executors.newSingleThreadExecutor()
    val cameraProviderFuture = ProcessCameraProvider.getInstance(context)

    cameraProviderFuture.addListener({
        val cameraProvider = cameraProviderFuture.get()

        val preview = Preview.Builder()
            .setTargetRotation(previewView.display.rotation)
            .build()
            .apply {
                setSurfaceProvider(previewView.surfaceProvider)
            }

        val imageAnalyzer = ImageAnalysis.Builder()
            .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
            .setOutputImageFormat(ImageAnalysis.OUTPUT_IMAGE_FORMAT_RGBA_8888)
            .build()
            .apply {
                setAnalyzer(executor) { imageProxy ->
                    try {
                        poseLandmarkerHelper.detectLiveStream(
                            imageProxy = imageProxy,
                            isFrontCamera = true
                        )
                    } catch (e: Exception) {
                        Log.e("PoseDetection", "Error in analyzer: ${e.message}")
                        imageProxy.close()
                    }
                }
            }

        val cameraSelector = CameraSelector.DEFAULT_FRONT_CAMERA

        try {
            cameraProvider.unbindAll()
            cameraProvider.bindToLifecycle(
                lifecycleOwner,
                cameraSelector,
                preview,
                imageAnalyzer
            )
        } catch (e: Exception) {
            Log.e("CameraX", "Failed to bind use cases", e)
        }

    }, ContextCompat.getMainExecutor(context))

    return container
}