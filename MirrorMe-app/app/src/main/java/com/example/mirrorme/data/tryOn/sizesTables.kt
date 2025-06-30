package com.example.mirrorme.data.tryOn

import io.github.sceneview.math.Scale
import io.github.sceneview.node.ModelNode

data class TshirtSize(
    val name: String,
    val chestCm: Float,
    val waistCm: Float, // 👈 New field
    val lengthCm: Float,
    val sleeveCm: Float
)



val womenTshirtSizes = mapOf(
    "S" to TshirtSize("S", chestCm = 84f, waistCm = 76f, lengthCm = 60f, sleeveCm = 14f),
    "M" to TshirtSize("M", chestCm = 89f, waistCm = 80f, lengthCm = 62f, sleeveCm = 15f),
    "L" to TshirtSize("L", chestCm = 94f, waistCm = 85f, lengthCm = 64f, sleeveCm = 16f),
    "XL" to TshirtSize("XL", chestCm = 99f, waistCm = 90f, lengthCm = 66f, sleeveCm = 17f),
    "XXL" to TshirtSize("XXL", chestCm = 104f, waistCm = 95f, lengthCm = 68f, sleeveCm = 18f)
)

val menTshirtSizes = mapOf(
    "S" to TshirtSize("S", chestCm = 92f, waistCm = 82f, lengthCm = 68f, sleeveCm = 22f),
    "M" to TshirtSize("M", chestCm = 97f, waistCm = 87f, lengthCm = 70f, sleeveCm = 23f),
    "L" to TshirtSize("L", chestCm = 102f, waistCm = 92f, lengthCm = 72f, sleeveCm = 24f),
    "XL" to TshirtSize("XL", chestCm = 107f, waistCm = 97f, lengthCm = 74f, sleeveCm = 25f),
    "XXL" to TshirtSize("XXL", chestCm = 112f, waistCm = 102f, lengthCm = 76f, sleeveCm = 26f)
)


fun scaleModelToSize(modelNode: ModelNode, size: TshirtSize) {
    val baseChest = 92f  // Men's S
    val baseLength = 65f
    val baseSleeve = 17f

    val scaleX = size.chestCm / baseChest
    val scaleY = size.lengthCm / baseLength
    val scaleZ = size.sleeveCm / baseSleeve

    modelNode.scale = Scale(scaleX, scaleY, scaleZ)
}




