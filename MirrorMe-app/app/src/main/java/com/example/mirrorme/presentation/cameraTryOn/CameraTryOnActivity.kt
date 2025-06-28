package com.example.mirrorme.presentation.cameraTryOn

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
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

class CameraTryOnActivity : ComponentActivity() {
    private var modelNodeRef: ModelNode? = null
    private var lastShoulderDistancePx: Float? = null
    private val viewModel: View3DViewModel by viewModels()

    private lateinit var requestPermissionLauncher: androidx.activity.result.ActivityResultLauncher<String>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestPermissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
                if (isGranted) {
                    viewModel.loadProfile()  // ✅ Start loading profile after permission
                    setContent {
                        ProfileObserverScreen()
                    }
                } else {
                    setContent {
                        Text("Camera permission denied. Please enable it in settings.")
                    }
                }
            }

        checkCameraPermissionAndStart(
            context = this,
            setupTryOnUI = {
                viewModel.loadProfile()  // ✅ Also load profile here
                setContent {
                    ProfileObserverScreen()
                }
            },
            requestPermissionLauncher = requestPermissionLauncher
        )
    }

    private fun TryOnUI(profile: Profile) {
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
                    selectedSize = selectedSize, // ✅ pass state
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
                    profile = profile
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
                            selectedSize.value = sizeList[index] // ✅ update state
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

    @Composable
    fun ARWithCameraPreview(
        selectedSize: State<TshirtSize>,
        selectedColorIndex: Int,
        onRecommendedSizeChange: (String) -> Unit,
        onFitInsightsChange: (String, String) -> Unit,
        onLandmarkPositionsChange: (Offset?, Offset?) -> Unit,
        profile: Profile
    ) {
        AndroidView(
            factory = { context ->
                createAccurateCameraWithPoseAnd3D(
                    context = context,
                    lifecycleOwner = this,
                    onPoseResult = { resultBundle ->
                        runOnUiThread {
                            modelNodeRef?.let {
                                val result =
                                    resultBundle.results.firstOrNull() ?: return@runOnUiThread
                                handlePoseResult(
                                    result,
                                    it,
                                    onRecommendedSizeChange,
                                    onFitInsightsChange,
                                    onLandmarkPositionsChange,
                                    selectedSize.value,  // ✅ always latest size
                                    updateShoulderDistancePx = { shoulderDistancePx ->
                                        lastShoulderDistancePx = shoulderDistancePx
                                    },
                                    profile
                                )
                            }
                        }
                    },
                    onModelReady = { modelNode ->
                        modelNodeRef = modelNode
                        scaleModelToSize(modelNode, selectedSize.value)
                        applyColorToModel(modelNode, colorList[selectedColorIndex])
                    }
                )
            }
        )
    }
    @Composable
    fun ProfileObserverScreen() {
        val profile = viewModel.profileUiState
        val isLoading = viewModel.isLoading
        val errorMessage = viewModel.errorMessage

        when {
            isLoading -> {
                Text("Loading profile...")
            }
            errorMessage != null -> {
                Text("Error: $errorMessage")
            }
            profile != null -> {
                TryOnUI(profile)
            }
        }
    }

}