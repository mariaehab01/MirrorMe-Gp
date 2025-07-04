package com.example.mirrorme.presentation.cameraTryOn.utils
import androidx.compose.ui.geometry.Offset
import com.example.mirrorme.data.tryOn.predictFitInsights
import com.google.mediapipe.tasks.vision.poselandmarker.PoseLandmarkerResult
import io.github.sceneview.math.Position
import io.github.sceneview.node.ModelNode
import com.example.mirrorme.data.tryOn.TshirtSize
import com.example.mirrorme.domain.model.Profile
import kotlin.math.pow
import kotlin.math.sqrt

/**
 * Handles the result of the pose landmarker and updates the model position, size recommendation,
 * fit insights,calculates the shoulder distance, and landmark positions.
 *This function is called every time a pose is detected from the camera*
 * */
fun handlePoseResult(
    result: PoseLandmarkerResult,
    modelNode: ModelNode,
    onRecommendedSizeChange: (String) -> Unit,
    onFitInsightsChange: (String, String) -> Unit,
    onLandmarkPositionsChange: (Offset?, Offset?) -> Unit,
    selectedSize: TshirtSize,
    updateShoulderDistancePx: (Float) -> Unit,
    profile: Profile,
) {
    // Check if the result contains landmarks
    val poseLandmarks = result.landmarks().firstOrNull() ?: return

    //get the left and right shoulder landmarks
    val leftShoulder = poseLandmarks[11]
    val rightShoulder = poseLandmarks[12]

    //coordinates of the left and right shoulders, normalized to the range [0, 1]
    val lx = leftShoulder.x()
    val ly = leftShoulder.y()
    val rx = rightShoulder.x()
    val ry = rightShoulder.y()

    // Calculate the center position of the shoulders
    val centerX = (lx + rx) / 2f
    val centerY = (ly + ry) / 2f
    val pixelScale = 0.005f // Scale factor to convert normalized coordinates to pixel coordinates
    val yOffset = 300f // Offset to adjust the vertical position of the model

    // Set the position of the model node based on the center position of the shoulders
    modelNode.position = Position(
        x = centerX * pixelScale, // Convert normalized x to pixel scale
        y = -(centerY + yOffset) * pixelScale,
        z = -1.5f
    )

    // Calculate the shoulder distance in pixels
    val shoulderDistancePx = sqrt((lx - rx).pow(2) + (ly - ry).pow(2))
    updateShoulderDistancePx(shoulderDistancePx)  // Update the shoulder distance

    val recommendedSize = recommendSize(
        heightCm = profile.height.toFloat(),
        weightKg = profile.weight.toFloat(),
        shoulderDistancePx = shoulderDistancePx,
        imageWidthPx = 640,
        userBodyShape = profile.body_shape,
        gender = if (profile.gender.lowercase() == "female") "women" else "men"
    )
    // Update the UI with the recommended size
    onRecommendedSizeChange(recommendedSize.name)

    val (chestFitText, waistFitText) = predictFitInsights(
        shoulderDistancePx = shoulderDistancePx,
        imageWidthPx = 640,
        userHeightCm = profile.height.toFloat(),
        userWeightKg = profile.weight.toFloat(),
        selectedSize = selectedSize
    )
    // Update the fit insights in the UI
    onFitInsightsChange(chestFitText, waistFitText)

    // get the scale of the model node
    val scaleX = modelNode.scale.x
    val scaleY = modelNode.scale.y
    val xOffset = 0.82f // Offset to adjust the position of the chest and waist landmarks

    // Calculate the 3D positions for the chest and waist landmarks based on the model node position
    val chest3D = Position(
        x = modelNode.position.x + ((xOffset + 0.01f) * scaleX),
        y = modelNode.position.y + (0.1f * scaleY * 1.5f),
        z = -modelNode.position.z
    )

    val waist3D = Position(
        x = modelNode.position.x + (xOffset * scaleX),
        y = modelNode.position.y - (0.2f * scaleY * 1.5f),
        z = modelNode.position.z
    )

    // Convert 3D Position to Screen Coordinates
    fun positionToScreenOffset(position: Position): Offset {
        val scaleFactor = 780f
        return Offset(position.x * scaleFactor, -position.y * scaleFactor)
    }

    // Get the screen positions for the chest and waist landmarks
    val chestPos = positionToScreenOffset(chest3D)
    val waistPos = positionToScreenOffset(waist3D)

    // Update the UI with the landmark positions
    onLandmarkPositionsChange(chestPos, waistPos)
}
