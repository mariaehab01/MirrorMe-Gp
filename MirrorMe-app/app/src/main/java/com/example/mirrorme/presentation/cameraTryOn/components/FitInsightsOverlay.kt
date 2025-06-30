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

@Composable
fun FitInsightsOverlay(
    chestPosition: Offset?,
    waistPosition: Offset?,
    chestFit: String,
    waistFit: String,
) {
    Canvas(modifier = Modifier.fillMaxSize()) {
        chestPosition?.let {
            drawFitLineWithLabel(
                start = it,
                end = Offset(it.x + 150f, it.y- 150f),
                fitText = chestFit,
                color = getFitColor(chestFit)
            )
        }

        waistPosition?.let {
            drawFitLineWithLabel(
                start = it,
                end = Offset(it.x + 150f, it.y + 150f),
                fitText = waistFit,
                color = getFitColor(waistFit)
            )
        }

    }
}

fun DrawScope.drawFitLineWithLabel(start: Offset, end: Offset, fitText: String, color: Color) {
    drawLine(color = color, start = start, end = end, strokeWidth = 4f)
    drawContext.canvas.nativeCanvas.apply {
        val paint = Paint().apply {
            this.color = android.graphics.Color.WHITE
            textSize = 40f
        }
        drawText(fitText, end.x, end.y, paint)
    }
}

fun getFitColor(fit: String): Color {
    return when (fit.lowercase()) {
        "perfect" -> Color.Green
        "slightly loose", "loose" -> Color.Yellow
        "tight" -> Color.Red
        else -> Color.Gray
    }
}

