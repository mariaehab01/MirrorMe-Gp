package com.example.mirrorme.presentation.cameraTryOn.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.nativeCanvas
import android.graphics.Paint
/** * FitInsightsOverlay.kt
 *
 * This file contains a composable function that draws fit insights overlay on the camera view.
 * It displays lines and labels indicating the fit of the chest and waist based on their positions.
 */
@Composable
fun FitInsightsOverlay(
    chestPosition: Offset?,
    waistPosition: Offset?,
    chestFit: String,
    waistFit: String,
) {
    // Draw the fit insights overlay on the camera view
    Canvas(modifier = Modifier.fillMaxSize()) {
        chestPosition?.let {
            drawFitLineWithLabel(
                start = it, // Start position of the chest line(as detected by the pose landmarker)
                end = Offset(it.x + 150f, it.y- 150f), // End position of the chest line
                fitText = chestFit,
                color = getFitColor(chestFit)
            )
        }

        waistPosition?.let {
            drawFitLineWithLabel(
                start = it, // Start position of the waist line(as detected by the pose landmarker)
                end = Offset(it.x + 150f, it.y + 150f), // End position of the waist line
                fitText = waistFit,
                color = getFitColor(waistFit)
            )
        }

    }
}

// Extension function to draws a line from the start to the end position and labels it with the fit text.
fun DrawScope.drawFitLineWithLabel(start: Offset, end: Offset, fitText: String, color: Color) {
    drawLine(color = color, start = start, end = end, strokeWidth = 4f)
    drawContext.canvas.nativeCanvas.apply {
        val paint = Paint().apply {
            this.color = android.graphics.Color.BLUE
            textSize = 40f
        }
        drawText(fitText, end.x, end.y, paint)
    }
}

// function to get the color based on the fit type
fun getFitColor(fit: String): Color {
    return when (fit.lowercase()) {
        "perfect" -> Color.Green
        "slightly loose", "loose" -> Color.Yellow
        "tight" -> Color.Red
        else -> Color.Gray
    }
}

