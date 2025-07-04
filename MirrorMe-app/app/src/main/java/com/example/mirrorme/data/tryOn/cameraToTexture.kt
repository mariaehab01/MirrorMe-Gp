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

/**
 * Creates a camera view with a 3D model and pose detection capabilities.
 * This function sets up the camera, loads a 3D model, and processes pose detection results.
 * combines three major things into one Android View:
 *
 * Camera preview (CameraX)
 *
 * Pose detection (MediaPipe)
 *
 * 3D model rendering (SceneView)
 * @return A FrameLayout containing the camera preview and scene view with the 3D model.
 */
fun createAccurateCameraWithPoseAnd3D(
    context: Context,
    lifecycleOwner: LifecycleOwner,
    onPoseResult: (PoseLandmarkerHelper.ResultBundle) -> Unit,
    setLoading: (Boolean) -> Unit,
    onModelReady: (ModelNode) -> Unit,
    modelPath: String

): View {
    // Create a FrameLayout to hold the camera preview and scene view
    val container = FrameLayout(context)

    // Create a PreviewView for camera preview
    //This is used to show the camera feed, scaled to fit the screen.
    val previewView = PreviewView(context).apply {
        scaleType = PreviewView.ScaleType.FIT_CENTER
        layoutParams = FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.MATCH_PARENT,
            FrameLayout.LayoutParams.MATCH_PARENT
        )
    }
    container.addView(previewView)

    // Create a SceneView for rendering the 3D model on top of the camera preview
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

    // Load the 3D clothing model into the SceneView
    loadModelIntoScene(
        context = context,
        sceneView = sceneView,
        modelPath = modelPath,
        onModelReady = onModelReady,
        setLoading = setLoading
    )

    // Pose detection helper
    //Continuously detects body landmarks from the live camera stream
    // calling onPoseResult with the results.
    val poseLandmarkerHelper = PoseLandmarkerHelper(
        runningMode = RunningMode.LIVE_STREAM,
        context = context,
        poseLandmarkerHelperListener = object : PoseLandmarkerHelper.LandmarkerListener {
            override fun onResults(resultBundle: PoseLandmarkerHelper.ResultBundle) {
                onPoseResult(resultBundle) // Handle the pose detection results
            }

            override fun onError(error: String, errorCode: Int) {
                Log.e("PoseDetection", "PoseLandmarker error: $error")
            }
        }
    )

    //cameraX setup
    val executor = Executors.newSingleThreadExecutor() // Executor for camera operations
    // Get the ProcessCameraProvider instance
    val cameraProviderFuture = ProcessCameraProvider.getInstance(context)

    cameraProviderFuture.addListener({ // listener called when the camera provider is ready
        val cameraProvider = cameraProviderFuture.get()

        //Displays the camera feed on PreviewView
        val preview = Preview.Builder()
            .setTargetRotation(previewView.display.rotation)
            .build()
            .apply {
                setSurfaceProvider(previewView.surfaceProvider)
            }

        // ImageAnalysis for processing camera frames
        // This is used to analyze the camera frames for pose detection
        val imageAnalyzer = ImageAnalysis.Builder()
            .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
            .setOutputImageFormat(ImageAnalysis.OUTPUT_IMAGE_FORMAT_RGBA_8888)
            .build()
            .apply {
                setAnalyzer(executor) { imageProxy -> //Each frame from the camera is sent to MediaPipe for pose landmark detection
                                                      //The result is passed to your onPoseResult handler
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

        // CameraSelector for selecting the front camera
        val cameraSelector = CameraSelector.DEFAULT_FRONT_CAMERA

        // Bind the camera use cases to the lifecycle
        //It ensures the camera shuts down and restarts with the activity/fragment
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

   //return the container with the camera preview and scene view
    return container
}