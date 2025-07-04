package com.example.mirrorme.presentation.auth.composes

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mirrorme.ui.theme.mainBlue

sealed class BodyInfoItem {
    data class ImageItem(
        val painter: Painter,
        val title: String,
        val width: Dp = 80.dp,
        val height: Dp = 120.dp
    ) : BodyInfoItem()

    data class ColorItem(
        val color: Color,
        val title: String = ""
    ) : BodyInfoItem()
}

@Composable
fun BodyInfoScroller(
    title: String,
    items: List<BodyInfoItem>,
    shape: RoundedCornerShape = RoundedCornerShape(12.dp),
    onItemSelected: (Int, String) -> Unit
) {
    var selectedIndex by remember { mutableStateOf(0) }
    val scrollState = rememberScrollState()

    Column {
        Text(
            text = title,
            fontSize = 22.sp,
            fontWeight = FontWeight.Medium,
            color = mainBlue,
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(scrollState)
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            items.forEachIndexed { index, item ->
                val isSelected = index == selectedIndex
                val itemName = when (item) {
                    is BodyInfoItem.ImageItem -> item.title
                    is BodyInfoItem.ColorItem -> item.title
                }

                val borderModifier = Modifier.border(
                    width = if (isSelected) 3.dp else 0.dp,
                    color = if (isSelected) Color.Cyan else Color.Transparent,
                    shape = shape
                )

                when (item) {
                    is BodyInfoItem.ImageItem -> {
                        Box(
                            modifier = Modifier
                                .size(width = item.width, height = item.height)
                                .clip(shape)
                                .then(borderModifier)
                                .clickable {
                                    selectedIndex = index
                                    onItemSelected(index, itemName)
                                }
                        ) {
                            Image(
                                painter = item.painter,
                                contentDescription = itemName,
                                modifier = Modifier.fillMaxSize()
                            )
                        }
                    }
                    is BodyInfoItem.ColorItem -> {
                        Box(
                            modifier = Modifier
                                .size(35.dp)
                                .clip(shape)
                                .then(borderModifier)
                                .background(item.color)
                                .clickable {
                                    selectedIndex = index
                                    onItemSelected(index, itemName)
                                }
                        )
                    }
                }
            }
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun BodyInfoScrollerPreview() {
//    val items = listOf(
//        BodyInfoItem.ImageItem(painterResource(R.drawable.m_body1), 80.dp, 120.dp),
//        BodyInfoItem.ColorItem(Color(0xFFF8D0C6)),
//        BodyInfoItem.ColorItem(Color(0xFFFFE0B2)),
//        BodyInfoItem.ColorItem(Color(0xFFF8BF8D))
//    )
//    val nameMap = mapOf(
//        items[0] to "Athletic",
//        items[1] to "Fair1",
//        items[2] to "Fair2",
//        items[3] to "Light"
//    )
//
//    BodyInfoScroller(
//        title = "Body Shapes",
//        items = items,
//        nameMap = nameMap,
//        onItemSelected = { index, name -> println("Selected: $index, $name") }
//    )
//}