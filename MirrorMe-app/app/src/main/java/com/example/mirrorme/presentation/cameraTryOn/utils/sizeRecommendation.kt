package com.example.mirrorme.presentation.cameraTryOn.utils

import android.util.Log
import com.example.mirrorme.data.tryOn.TshirtSize
import com.example.mirrorme.data.tryOn.menTshirtSizes
import com.example.mirrorme.data.tryOn.womenTshirtSizes

/**
 * Recommends a T-shirt size based on height, weight, shoulder distance, image width,
 * user body shape
 * */
fun recommendSize(
    heightCm: Float,
    weightKg: Float,
    shoulderDistancePx: Float,
    imageWidthPx: Int,
    userBodyShape: String,
    gender: String
): TshirtSize {
    //Picks the correct sizing system depending on gender.
    val sizeTable = when (gender.lowercase()) {
        "men" -> menTshirtSizes.values.toList()
        "women" -> womenTshirtSizes.values.toList()
        else -> menTshirtSizes.values.toList() // Unisex fallback
    }

    // ------ 1. Base Size by Height and Weight ------
    var baseSizeName = when {
        heightCm < 150 && weightKg < 45 -> "S"
        heightCm in 150f..160f && weightKg < 50f -> "S"
        heightCm in 150f..160f && weightKg in 50f..60f -> "M"
        heightCm in 150f..160f && weightKg in 60f..70f -> "L"
        heightCm in 150f..160f && weightKg in 70f..80f -> "XL"
        heightCm in 150f..160f && weightKg > 80f -> "XXL"


        heightCm in 160f..170f && weightKg < 55f -> "S"
        heightCm in 160f..170f && weightKg in 55f..65f -> "M"
        heightCm in 160f..170f && weightKg in 65f..75f -> "L"

        heightCm in 170f..180f && weightKg < 65f -> "M"
        heightCm in 170f..180f && weightKg in 65f..80f -> "L"
        heightCm in 170f..180f && weightKg > 80f -> "XL"

        heightCm > 180f && weightKg < 75f -> "L"
        heightCm > 180f && weightKg in 75f..90f -> "XL"
        else -> "XXL"
    }


    // ------ 2. Adjust for Shoulder Width ------
    val shoulderWidthCm = (shoulderDistancePx / imageWidthPx) * heightCm // Rough pixel-to-cm scale

    Log.d("SizeRecommend", "Shoulder Width: $shoulderWidthCm cm")

    if (shoulderWidthCm > 45) { // If shoulder width is larger than 45 cm, increase size
        if (baseSizeName == "S") baseSizeName = "M"
        else if (baseSizeName == "M") baseSizeName = "L"
        else if (baseSizeName == "L") baseSizeName = "XL"
        else if (baseSizeName == "XL") baseSizeName = "XXL"
    } else if (shoulderWidthCm < 35) { // If shoulder width is smaller than 35 cm, decrease size
        if (baseSizeName == "XL") baseSizeName = "L"
        else if (baseSizeName == "L") baseSizeName = "M"
    }

    // ------ 3. Body Shape Adjustment ------
    when (userBodyShape.lowercase()) {
        "hourglass","pear" -> {
            if (baseSizeName != "S") {
                baseSizeName = sizeTable.getOrNull(sizeTable.indexOfFirst { it.name == baseSizeName } - 1)?.name ?: baseSizeName
            }
        }
        "inverted triangle", "triangle" -> {
            baseSizeName = sizeTable.getOrNull(sizeTable.indexOfFirst { it.name == baseSizeName } + 1)?.name ?: baseSizeName
        }
        // Rectangle / Oval: no change
    }

    Log.d("SizeRecommend", "Recommended Size: $baseSizeName")
    return sizeTable.first { it.name == baseSizeName }
}
