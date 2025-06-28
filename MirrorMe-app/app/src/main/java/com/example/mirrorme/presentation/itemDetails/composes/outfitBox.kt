package com.example.mirrorme.presentation.itemDetails.composes

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mirrorme.ui.theme.popColor

@Composable
fun OutfitContainer() {
    Spacer(modifier = Modifier.height(16.dp))
    Text("Generated Outfit", fontSize = 18.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(horizontal = 30.dp))
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .padding(horizontal = 30.dp)
            .border(2.dp, popColor, RoundedCornerShape(8.dp))
            .background(Color.LightGray),
        contentAlignment = Alignment.Center
    ) {
        Text("Outfit Image Placeholder", color = Color.DarkGray)
    }
}
