package com.example.mirrorme.data.tryOn

/** * Predicts fit insights based on shoulder distance, image width, user height, weight, and selected size.
 *
 * @return A pair containing fit insights for chest and waist.
 */
fun predictFitInsights(
    shoulderDistancePx: Float,
    imageWidthPx: Int,
    userHeightCm: Float,
    userWeightKg: Float,
    selectedSize: TshirtSize
): Pair<String, String> {
    // --- Step 1: Estimate real shoulder width ---
    val shoulderRatio = shoulderDistancePx / imageWidthPx
    val shoulderWidthCm = shoulderRatio * userHeightCm  // 👈 Project shoulder ratio on height

    // --- Step 2: Estimate chest and waist using BMI-based proportional logic ---
    val heightM = userHeightCm / 100f
    val bmi = userWeightKg / (heightM * heightM)  // 👈 Body Mass Index

    val chestEstimate = when {
        bmi < 18.5 -> 0.50f * userHeightCm  // Underweight
        bmi < 24.9 -> 0.52f * userHeightCm  // Normal
        bmi < 30f -> 0.56f * userHeightCm   // Overweight
        else -> 0.63f * userHeightCm         // Obese
    }

    val waistEstimate = when {
        bmi < 18.5 -> 0.36f * userHeightCm
        bmi < 24.9 -> 0.42f * userHeightCm
        bmi < 30f -> 0.48f * userHeightCm
        else -> 0.55f * userHeightCm
    }

    // --- Step 3: Compare actual measurements to selected size ---
    fun getFit(sizeCm: Float, bodyCm: Float): String {
        val diff = bodyCm - sizeCm

        return when {
            diff > 4f -> "too tight"      // 👈 body much bigger than shirt
            diff in 2f..4f -> "tight"     // 👈 body a bit bigger
            diff in -2f..2f -> "perfect"  // 👈 close fit (±2cm)
            diff < -6f -> "loose"         // 👈 shirt much bigger than body
            else -> "slightly loose"      // 👈 medium looseness
        }
    }


    // --- Step 4: Evaluate fit for chest, waist, and shoulder ---
    val chestFit = getFit(selectedSize.chestCm, chestEstimate)
    val waistFit = getFit(selectedSize.waistCm, waistEstimate)
//    val shoulderFit = getFit(selectedSize.chestCm * 0.75f, shoulderWidthCm) // 👈 Estimate garment shoulder width from chest

    return Pair(chestFit, waistFit)
}
