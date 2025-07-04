package com.example.mirrorme.presentation.cameraTryOn.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mirrorme.ui.theme.popColor

/**
 * Composable function to display the recommended size for the user.
 * It shows a text label and the recommended size in a prominent color.
 *
 */
@Composable
fun SizeRecommendationUI(
    recommendedSize: String,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 60.dp),
        contentAlignment = Alignment.TopCenter
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Recommended size: ",
                color = Color.White,
                fontSize = 20.sp
            )
            Text(
                text = recommendedSize,
                color = popColor,
                fontSize = 24.sp
            )
        }
    }
}
