package com.example.mirrorme.presentation.cameraTryOn.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mirrorme.data.tryOn.TshirtSize
import com.example.mirrorme.ui.theme.popColor

@Composable
fun SizeSelector(
    sizeList: List<TshirtSize>,
    selectedIndex: Int,
    onSizeSelected: (Int) -> Unit
) {
    val itemWidth: Dp = 40.dp     // Width for each size box
    val spacing: Dp = 8.dp       // Space between each size
    val totalWidth = (itemWidth * 5) + (spacing * 4)  // ✅Total width for exactly 5 items

    Box(
        modifier = Modifier
            .padding(start = 30.dp, top = 8.dp, bottom = 8.dp) // ✅ Keep your original paddings
            .width(totalWidth)    // ✅ Only take width for 5 items
    ) {
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(spacing),
            contentPadding = PaddingValues(horizontal = 15.dp)
        ) {
            itemsIndexed(sizeList) { index, size ->
                val isSelected = index == selectedIndex

                val scale by animateFloatAsState(
                    targetValue = if (isSelected) 1.2f else 1.0f,
                    label = "SizeScaleAnim"
                )
                val alpha by animateFloatAsState(
                    targetValue = if (isSelected) 1f else 0.4f,
                    label = "SizeAlphaAnim"
                )

                Box(
                    modifier = Modifier
                        .width(itemWidth)
                        .graphicsLayer {
                            scaleX = scale
                            scaleY = scale
                            this.alpha = alpha
                        }
                        .clickable { onSizeSelected(index) }
                ) {
                    Text(
                        text = size.name,
                        fontSize = if (isSelected) 22.sp else 16.sp,
                        color = if (isSelected) popColor else Color.White,
                    )
                }
            }
        }
    }
}
