package com.example.mirrorme.data.tryOn

import android.graphics.Color
import com.google.android.filament.MaterialInstance
import io.github.sceneview.node.ModelNode

val colorList = listOf(
    Color.RED,
    Color.GREEN,
    Color.BLUE,
    Color.YELLOW,
    Color.WHITE,
    Color.BLACK,
    Color.GRAY,
)

fun applyColorToModel(modelNode: ModelNode, colorInt: Int) {
    val r = Color.red(colorInt) / 255f
    val g = Color.green(colorInt) / 255f
    val b = Color.blue(colorInt) / 255f
    val a = 1.0f

    modelNode.modelInstance?.materialInstances?.forEach { materialInstance ->
        (materialInstance as? MaterialInstance)?.apply {
            setParameter("baseColorFactor", r, g, b, a)
        }
    }
}