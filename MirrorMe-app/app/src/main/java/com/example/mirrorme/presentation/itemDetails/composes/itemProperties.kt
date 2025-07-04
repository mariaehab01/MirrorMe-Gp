package com.example.mirrorme.presentation.itemDetails.composes

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.sharp.ArrowForwardIos
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.sharp.ArrowBackIosNew
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mirrorme.ui.theme.itemsBlue
import com.example.mirrorme.ui.theme.popColor
@Composable
fun ColorSizeQuantitySection(
    colors: List<Color>,
    sizes: List<String>,
    colorScrollStart: Int,
    sizeScrollStart: Int,
    selectedColorIndex: Int,
    selectedSizeIndex: Int,
    quantity: Int,
    onColorScrollChange: (Int) -> Unit,
    onSizeScrollChange: (Int) -> Unit,
    onColorSelect: (Int) -> Unit,
    onSizeSelect: (Int) -> Unit,
    onQuantityChange: (Int) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 30.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text("Color", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = itemsBlue)
            Spacer(modifier = Modifier.height(4.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                if (colorScrollStart > 0) {
                    Icon(Icons.Sharp.ArrowBackIosNew, "Scroll Left", tint = itemsBlue, modifier = Modifier.size(20.dp).clickable { onColorScrollChange(colorScrollStart - 1) })
                }
                Row(
                    Modifier.horizontalScroll(rememberScrollState()).padding(horizontal = 4.dp),
                    Arrangement.spacedBy(8.dp)
                ) {
                    val visibleColors = colors.drop(colorScrollStart).take(4)
                    visibleColors.forEachIndexed { index, color ->
                        Box(
                            Modifier.size(30.dp).clip(CircleShape).background(color)
                                .border(
                                    if (selectedColorIndex == colorScrollStart + index) 2.dp else 0.dp,
                                    if (selectedColorIndex == colorScrollStart + index) popColor else Color.Transparent,
                                    CircleShape
                                )
                                .clickable { onColorSelect(colorScrollStart + index) }
                        )
                    }
                }
                if (colorScrollStart + 4 < colors.size) {
                    Icon(Icons.AutoMirrored.Sharp.ArrowForwardIos, "Scroll Right", tint = itemsBlue, modifier = Modifier.size(20.dp).clickable { onColorScrollChange(colorScrollStart + 1) })
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // --- Size ---
            Text("Size", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = itemsBlue)
            Spacer(modifier = Modifier.height(4.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                if (sizeScrollStart > 0) {
                    Icon(Icons.Sharp.ArrowBackIosNew, "Scroll Left", tint = itemsBlue, modifier = Modifier.size(20.dp).clickable { onSizeScrollChange(sizeScrollStart - 1) })
                }
                Row(
                    Modifier.horizontalScroll(rememberScrollState()).padding(horizontal = 4.dp),
                    Arrangement.spacedBy(8.dp)
                ) {
                    val visibleSizes = sizes.drop(sizeScrollStart).take(4)
                    visibleSizes.forEachIndexed { index, size ->
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .size(30.dp)
                                .border(
                                    if (selectedSizeIndex == sizeScrollStart + index) 2.dp else 0.dp,
                                    if (selectedSizeIndex == sizeScrollStart + index) popColor else Color.Transparent,
                                    RoundedCornerShape(4.dp)
                                )
                                .clickable { onSizeSelect(sizeScrollStart + index) }
                        ) {
                            Text(size, fontSize = 14.sp, color = itemsBlue)
                        }
                    }
                }
                if (sizeScrollStart + 4 < sizes.size) {
                    Icon(Icons.AutoMirrored.Sharp.ArrowForwardIos, "Scroll Right", tint = itemsBlue, modifier = Modifier.size(20.dp).clickable { onSizeScrollChange(sizeScrollStart + 1) })
                }
            }
        }

        Spacer(modifier = Modifier.width(16.dp))

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("Available", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = itemsBlue)
            Spacer(modifier = Modifier.height(4.dp))
            Box(
                Modifier.width(50.dp).height(30.dp).border(1.dp, itemsBlue, RoundedCornerShape(4.dp)),
                Alignment.Center
            ) {
                Text("10", color = itemsBlue, fontSize = 14.sp)
            }

            Spacer(modifier = Modifier.height(12.dp))
            Text("Quantity", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = itemsBlue)
            Spacer(modifier = Modifier.height(4.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Remove, "Decrease", tint = itemsBlue, modifier = Modifier.size(24.dp).clickable { if (quantity > 1) onQuantityChange(quantity - 1) })
                Spacer(modifier = Modifier.width(8.dp))
                Box(
                    Modifier.width(40.dp).height(30.dp).border(1.dp, itemsBlue, RoundedCornerShape(4.dp)),
                    Alignment.Center
                ) {
                    Text(quantity.toString(), color = itemsBlue, fontSize = 14.sp)
                }
                Spacer(modifier = Modifier.width(8.dp))
                Icon(Icons.Default.Add, "Increase", tint = itemsBlue, modifier = Modifier.size(24.dp).clickable { onQuantityChange(quantity + 1) })
            }
        }
    }
}
