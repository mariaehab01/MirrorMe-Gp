package com.example.mirrorme.presentation.cameraTryOn

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.example.mirrorme.data.tryOn.TshirtSize
import com.example.mirrorme.data.tryOn.applyColorToModel
import com.example.mirrorme.data.tryOn.colorList
import com.example.mirrorme.data.tryOn.createAccurateCameraWithPoseAnd3D
import com.example.mirrorme.data.tryOn.predictFitInsights
import com.example.mirrorme.data.tryOn.scaleModelToSize
import com.example.mirrorme.data.tryOn.womenTshirtSizes
import com.example.mirrorme.domain.model.Profile
import com.example.mirrorme.presentation.cameraTryOn.components.ColorSelector
import com.example.mirrorme.presentation.cameraTryOn.components.FitInsightsOverlay
import com.example.mirrorme.presentation.cameraTryOn.components.SizeRecommendationUI
import com.example.mirrorme.presentation.cameraTryOn.components.SizeSelector
import com.example.mirrorme.presentation.cameraTryOn.utils.checkCameraPermissionAndStart
import com.example.mirrorme.presentation.cameraTryOn.utils.handlePoseResult
import io.github.sceneview.node.ModelNode

/**
 * CameraTryOnActivity is the main activity for the camera try-on feature.
 * It initializes the camera, loads the 3D model, and handles user interactions
 * for selecting size and color of the clothing item.
 * It also observes the user's profile to provide personalized recommendations.
 * handels pose detection and updates the 3D model accordingly.
 */
class CameraTryOnActivity : ComponentActivity() {
    //Reference to the loaded 3D t-shirt model, to allow updating size or color.
    private var modelNodeRef: ModelNode? = null
    //Last measured shoulder width in pixels (from pose detection)
    private var lastShoulderDistancePx: Float? = null
    private val viewModel: View3DViewModel by viewModels()
    //camera permission request launcher
    private lateinit var requestPermissionLauncher: androidx.activity.result.ActivityResultLauncher<String>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val modelUrl = intent.getStringExtra("MODEL_URL")

        requestPermissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
                if (isGranted) {
                    viewModel.loadProfile()  // Start loading profile after permission
                    setContent {
                        ProfileObserverScreen()
                    }
                } else {
                    setContent {
                        Text("Camera permission denied. Please enable it in settings.")
                    }
                }
            }
        //ensures permission is granted before rendering the camera + AR UI.
        checkCameraPermissionAndStart(
            context = this,
            setupTryOnUI = { // This lambda is called when permission is granted,
                            // loads the profile and sets the content of the tryon
                viewModel.loadProfile()
                setContent {
                    ProfileObserverScreen()
                }
            },
            requestPermissionLauncher = requestPermissionLauncher
        )
    }

    private fun TryOnUI(profile: Profile, modelUrl: String) {
        setContent {
            val sizeList = womenTshirtSizes.values.toList()
            var selectedSizeIndex by remember { mutableStateOf(0) }
            var selectedColorIndex by remember { mutableStateOf(0) }
            var recommendedSizeText by remember { mutableStateOf("") }

            var chestFit by remember { mutableStateOf("") }
            var waistFit by remember { mutableStateOf("") }
            var shoulderFit by remember { mutableStateOf("") }

            val selectedSize = remember { mutableStateOf(sizeList[0]) }

            var chestPosition by remember { mutableStateOf<Offset?>(null) }
            var waistPosition by remember { mutableStateOf<Offset?>(null) }

            Box(modifier = Modifier.fillMaxSize()) {
                ARWithCameraPreview(
                    selectedSize = selectedSize,
                    selectedColorIndex = selectedColorIndex,
                    onRecommendedSizeChange = { newText ->
                        recommendedSizeText = newText
                    },
                    onFitInsightsChange = { chest, waist ->
                        chestFit = chest
                        waistFit = waist
                    },
                    onLandmarkPositionsChange = { chestPos, waistPos ->
                        chestPosition = chestPos
                        waistPosition = waistPos
                    },
                    profile = profile,
                    modelUrl = modelUrl
                )

                SizeRecommendationUI(recommendedSizeText)

                FitInsightsOverlay(
                    chestPosition = chestPosition,
                    waistPosition = waistPosition,
                    chestFit = chestFit,
                    waistFit = waistFit,
                )

                Column(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 22.dp)
                ) {
                    SizeSelector(
                        sizeList = sizeList,
                        selectedIndex = selectedSizeIndex,
                        onSizeSelected = { index ->
                            selectedSizeIndex = index
                            selectedSize.value = sizeList[index] // update state
                            modelNodeRef?.let { scaleModelToSize(it, sizeList[index]) }

                            lastShoulderDistancePx?.let { shoulderPx ->
                                val (chest, waist) = predictFitInsights(
                                    shoulderDistancePx = shoulderPx,
                                    imageWidthPx = 640,
                                    userHeightCm = profile.height.toFloat(),
                                    userWeightKg = profile.weight.toFloat(),
                                    selectedSize = selectedSize.value
                                )
                                chestFit = chest
                                waistFit = waist
                            }
                        }
                    )

                    ColorSelector(
                        colorList = colorList,
                        selectedColorIndex = selectedColorIndex,
                        onColorSelected = { index ->
                            selectedColorIndex = index
                            modelNodeRef?.let { applyColorToModel(it, colorList[index]) }
                        }
                    )
                }
            }
        }
    }

    /**
     * Composable function that sets up the AR camera preview with the 3D-
     * model and MediaPipe pose processing.
     * It handles loading state, pose detection results, and model updates.
     */
    @Composable
    fun ARWithCameraPreview(
        selectedSize: State<TshirtSize>,
        selectedColorIndex: Int,
        onRecommendedSizeChange: (String) -> Unit,
        onFitInsightsChange: (String, String) -> Unit,
        onLandmarkPositionsChange: (Offset?, Offset?) -> Unit,
        profile: Profile,
        modelUrl: String
    ) {
        var isModelLoading by remember { mutableStateOf(false) }
        val lifecycleOwner = LocalLifecycleOwner.current

        Box(modifier = Modifier.fillMaxSize()) {
            AndroidView(
                factory = { context ->
                    createAccurateCameraWithPoseAnd3D(
                        context = context,
                        lifecycleOwner = lifecycleOwner,
                        setLoading = { isModelLoading = it },   // Pass Compose loading state
                        onPoseResult = { resultBundle ->     //Listens for pose result
                            runOnUiThread { // Update UI on the main thread
                                modelNodeRef?.let {
                                    val result =
                                        resultBundle.results.firstOrNull() ?: return@runOnUiThread // If no pose result, do nothing
                                    handlePoseResult(  //calls the function to handle pose result updates
                                        result,
                                        it,
                                        onRecommendedSizeChange,
                                        onFitInsightsChange,
                                        onLandmarkPositionsChange,
                                        selectedSize.value,
                                        updateShoulderDistancePx = { shoulderDistancePx ->
                                            lastShoulderDistancePx = shoulderDistancePx
                                        },
                                        profile
                                    )
                                }
                            }
                        },
                        onModelReady = { modelNode ->  // Listens for model loading completion
                            modelNodeRef = modelNode   // Store the reference to the model node
                            scaleModelToSize(modelNode, selectedSize.value) // Scale the model to the selected size
                            applyColorToModel(modelNode, colorList[selectedColorIndex]) // Apply the selected color to the model
                        },
                        modelPath = modelUrl
                    )
                }
            )
            // Show loading indicator when the model is being loaded
            if (isModelLoading) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(androidx.compose.ui.graphics.Color.Black.copy(alpha = 0.3f)),
                    contentAlignment = Alignment.Center
                ) {
                    androidx.compose.material3.CircularProgressIndicator()
                }
            }
        }
    }

    /**
     * Composable function that observes the ViewModel's profile state
     * and displays the Try-On UI once the profile is loaded.
     */
    @Composable
    fun ProfileObserverScreen() {
        val profile = viewModel.profileUiState
        val isLoading = viewModel.isLoading
        val errorMessage = viewModel.errorMessage
        val modelUrl = intent.getStringExtra("MODEL_URL") ?: ""

        when {
            isLoading -> {
                Text("Loading profile...")
            }
            errorMessage != null -> {
                Text("Error: $errorMessage")
            }
            profile != null -> {
                TryOnUI(profile, modelUrl)
            }
        }
    }

}