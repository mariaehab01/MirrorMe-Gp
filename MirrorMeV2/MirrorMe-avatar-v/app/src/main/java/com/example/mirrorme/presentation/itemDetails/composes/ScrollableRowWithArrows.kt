package com.example.mirrorme.presentation.itemDetails.composes

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.mirrorme.domain.model.Product
import com.example.mirrorme.ui.theme.itemsBlue

@Composable
fun ScrollableRowWithArrows(
    title: String,
    products: List<Product>,
    scrollStart: Int,
    onScrollChange: (Int) -> Unit,
    boxColor: Color,
    emptyMessage: String = "",
    onItemClick: ((Product) -> Unit)? = null
) {
    Text(title, fontSize = 18.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(horizontal = 30.dp))
    Log.d("scrolllllll", "into similaritylist : ${products.size} items")

    if (products.isEmpty() && emptyMessage.isNotEmpty()) {
        Text(
            text = emptyMessage,
            color = Color.Gray,
            fontSize = 14.sp,
            modifier = Modifier.padding(horizontal = 30.dp, vertical = 8.dp)
        )
    } else if (products.isNotEmpty()) {
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
                    modifier = Modifier
                        .size(20.dp)
                        .clickable { onScrollChange(scrollStart - 1) }
                )
            }

            Row(
                modifier = Modifier
                    .horizontalScroll(rememberScrollState())
                    .weight(1f),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                products.drop(scrollStart).take(4).forEach { product ->
                    Image(
                        painter = rememberAsyncImagePainter(product.imageUrl),
                        contentDescription = product.name,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(80.dp)
                            .background(boxColor)
                            .clickable { onItemClick?.invoke(product) }
                    )
                }
            }

            if (scrollStart + 4 < products.size) {
                Icon(
                    imageVector = Icons.AutoMirrored.Sharp.ArrowForwardIos,
                    contentDescription = "Scroll Right",
                    tint = itemsBlue,
                    modifier = Modifier
                        .size(20.dp)
                        .clickable { onScrollChange(scrollStart + 1) }
                )
            }
        }
    }
}
