package com.example.mirrorme.presentation.itemDetails.composes

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.mirrorme.ui.theme.mainBlue

@Composable
fun ProductImage(imageUrl: String) {
    Box(
        modifier = Modifier
            .width(331.dp)
            .height(211.dp)
            .border(3.dp, mainBlue, RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp))
            .clip(RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp))
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        AsyncImage(
            model = imageUrl,
            contentDescription = "Product Image",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Fit
        )
    }
}
