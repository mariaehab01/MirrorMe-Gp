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
    val poseLandmarks = result.landmarks().firstOrNull() ?: return

    val leftShoulder = poseLandmarks[11]
    val rightShoulder = poseLandmarks[12]

    val lx = leftShoulder.x()
    val ly = leftShoulder.y()
    val rx = rightShoulder.x()
    val ry = rightShoulder.y()

    val centerX = (lx + rx) / 2f
    val centerY = (ly + ry) / 2f
    val pixelScale = 0.005f
    val yOffset = 300f

    modelNode.position = Position(
        x = centerX * pixelScale,
        y = -(centerY + yOffset) * pixelScale,
        z = -1.5f
    )

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
    onRecommendedSizeChange(recommendedSize.name)

    val (chestFitText, waistFitText) = predictFitInsights(
        shoulderDistancePx = shoulderDistancePx,
        imageWidthPx = 640,
        userHeightCm = profile.height.toFloat(),
        userWeightKg = profile.weight.toFloat(),
        selectedSize = selectedSize
    )
    onFitInsightsChange(chestFitText, waistFitText)

    val scaleX = modelNode.scale.x
    val scaleY = modelNode.scale.y
    val xOffset = 0.82f

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

    fun positionToScreenOffset(position: Position): Offset {
        val scaleFactor = 780f
        return Offset(position.x * scaleFactor, -position.y * scaleFactor)
    }

    val chestPos = positionToScreenOffset(chest3D)
    val waistPos = positionToScreenOffset(waist3D)

    onLandmarkPositionsChange(chestPos, waistPos)
}
