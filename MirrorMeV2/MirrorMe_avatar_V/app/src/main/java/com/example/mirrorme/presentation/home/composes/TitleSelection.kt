package com.example.mirrorme.presentation.home.composes

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import com.example.mirrorme.ui.theme.lightBlue
import com.example.mirrorme.ui.theme.mainPink

@Composable
fun TitleSection(category: String, topPadding: Dp = 6.dp) {
    Text(
        text = "~${category.uppercase()}~",
        fontSize = 18.sp,
        fontWeight = FontWeight.Medium,
        color = lightBlue,
        textAlign = TextAlign.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = topPadding, bottom = 16.dp)

    )
}
