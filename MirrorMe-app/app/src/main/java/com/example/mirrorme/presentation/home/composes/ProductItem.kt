package com.example.mirrorme.presentation.home.composes

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.mirrorme.domain.model.Product
import com.example.mirrorme.ui.theme.mainBlue

@Composable
fun ProductItem(item: Product, onClick: () -> Unit = {}) {
//    Column(horizontalAlignment = Alignment.CenterHorizontally,
//        modifier = Modifier
//        .clickable { onClick(
//            //navigate to product details
//
//        ) }
//        .padding(4.dp)
//    ){
//        Image(
//            painter = painterResource(id = item.imageRes),
//            contentDescription = item.name,
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(160.dp)
//                .clip(RoundedCornerShape(16.dp))
//                .border(
//                    width = 1.dp,
//                    color = mainBlue,
//                    shape = RoundedCornerShape(16.dp)
//                ),
//            contentScale = ContentScale.Crop
//        )
//        Spacer(modifier = Modifier.height(6.dp))
//        Text(item.name, fontSize = 14.sp)
//        Text(item.price, fontSize = 13.sp, color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f))
//    }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable { onClick() }
    ) {
        Log.d("ProductImage", "Attempting to load: ${item.imageUrl}")

        AsyncImage( // ✅ loads image from URL
            model = item.imageUrl,
            contentDescription = item.name,
            modifier = Modifier
                .fillMaxWidth()
                .height(160.dp)
                .clip(RoundedCornerShape(16.dp))
                .border(
                    width = 1.dp,
                    color = mainBlue,
                    shape = RoundedCornerShape(16.dp)
                ),
            contentScale = ContentScale.Crop,
                    onError = {
                Log.e("ProductImage", "Error loading image: ${item.imageUrl}")
            },
            onSuccess = {
                Log.d("ProductImage", "Image loaded successfully: ${item.imageUrl}")
            }
        )

        Spacer(modifier = Modifier.height(6.dp))
        Text(item.name, fontSize = 14.sp)
        Text("€ ${item.price}", fontSize = 13.sp, color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f))
    }
}
