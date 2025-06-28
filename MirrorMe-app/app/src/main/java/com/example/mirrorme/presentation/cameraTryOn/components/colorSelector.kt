package com.example.mirrorme.presentation.cameraTryOn.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.mirrorme.ui.theme.popColor

@Composable
fun ColorSelector(
    colorList: List<Int>,
    selectedColorIndex: Int,
    onColorSelected: (Int) -> Unit
) {
    val itemSize: Dp = 30.dp
    val spacing: Dp = 16.dp
    val totalWidth = (itemSize * 6) + (spacing * 4)  // ✅ Width for exactly 5 items + 4 spacings

    Box(
        modifier = Modifier
            .padding(start = 30.dp, top = 8.dp, bottom = 8.dp) // ✅ Keep your original paddings
            .width(totalWidth)
    ) {
        LazyRow(
            contentPadding = PaddingValues(horizontal = 15.dp),
            horizontalArrangement = Arrangement.spacedBy(spacing)
        ) {
            itemsIndexed(colorList) { index, color ->
                Box(
                    modifier = Modifier
                        .size(itemSize)
                        .background(
                            color = Color(color),
                            shape = CircleShape
                        )
                        .border(
                            width = if (index == selectedColorIndex) 3.dp else 1.dp,
                            color = if (index == selectedColorIndex) popColor else Color(color),
                            shape = CircleShape
                        )
                        .clickable { onColorSelected(index) }
                )
            }
        }
    }
}
