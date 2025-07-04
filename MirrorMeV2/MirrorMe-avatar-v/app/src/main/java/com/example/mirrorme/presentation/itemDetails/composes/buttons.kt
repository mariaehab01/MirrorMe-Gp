package com.example.mirrorme.presentation.itemDetails.composes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.mirrorme.ui.theme.mainBlue
import com.example.mirrorme.ui.theme.mainPink
import com.example.mirrorme.ui.theme.popColor

@Composable
fun ActionButtons(onTryOn: () -> Unit, onGenerateOutfit: () -> Unit, onAddToBag: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 30.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Button(
            onClick = { onTryOn() },
            modifier = Modifier.weight(1f),
            colors = ButtonDefaults.buttonColors(containerColor = popColor)
        ) {
            Text(text = "Try-On", color = mainBlue)
        }
        Spacer(modifier = Modifier.width(16.dp))
        Button(
            onClick = { onGenerateOutfit() },
            modifier = Modifier.weight(1f),
            colors = ButtonDefaults.buttonColors(containerColor = mainPink)
        ) {
            Text(text = "Generate Outfit", color = Color.White)
        }
    }

    Spacer(modifier = Modifier.height(12.dp))

    // Add To Bag Button
    Button(
        onClick = { onAddToBag() },
        modifier = Modifier
            .padding(horizontal = 30.dp)
            .fillMaxWidth(0.5f),
        colors = ButtonDefaults.buttonColors(containerColor = mainBlue)
    ) {
        Text(text = "Add To Bag", color = Color.White)
    }
}
