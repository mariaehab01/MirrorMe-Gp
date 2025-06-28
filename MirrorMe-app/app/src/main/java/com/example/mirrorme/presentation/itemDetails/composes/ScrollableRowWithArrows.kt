package com.example.mirrorme.presentation.itemDetails.composes

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.sharp.ArrowForwardIos
import androidx.compose.material.icons.sharp.ArrowBackIosNew
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mirrorme.ui.theme.itemsBlue

@Composable
fun ScrollableRowWithArrows(
    title: String,
    itemCount: Int,
    scrollStart: Int,
    onScrollChange: (Int) -> Unit,
    boxColor: Color
) {
    Text(title, fontSize = 18.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(horizontal = 30.dp))

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 30.dp)
    ) {
        if (scrollStart > 0) {
            Icon(
                imageVector = Icons.Sharp.ArrowBackIosNew,
                contentDescription = "Scroll Left",
                tint = itemsBlue,
                modifier = Modifier.size(20.dp).clickable { onScrollChange(scrollStart - 1) }
            )
        }

        Row(
            modifier = Modifier
                .horizontalScroll(rememberScrollState())
                .weight(1f),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            (0 until itemCount).drop(scrollStart).take(4).forEach { _ ->
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .background(boxColor)
                )
            }
        }

        if (scrollStart + 4 < itemCount) {
            Icon(
                imageVector = Icons.AutoMirrored.Sharp.ArrowForwardIos,
                contentDescription = "Scroll Right",
                tint = itemsBlue,
                modifier = Modifier.size(20.dp).clickable { onScrollChange(scrollStart + 1) }
            )
        }
    }
}
