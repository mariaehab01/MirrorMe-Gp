package com.example.mirrorme.presentation.cart.composes

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.mirrorme.domain.model.CartItem
import com.example.mirrorme.presentation.cart.CartViewModel
import com.example.mirrorme.ui.theme.mainBlue

@Composable
fun CartItemCard(item: CartItem, viewModel: CartViewModel) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
            AsyncImage(
                model = item.imageRes,
                contentDescription = null,
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f).padding(top = 24.dp)) {
                Text(
                    text = item.name,
                    fontWeight = FontWeight.Bold,
                    color = mainBlue
                )

                Spacer(modifier = Modifier.height(2.dp))

                Row {
                    Text(text = "Size: ", fontSize = 13.sp, color = Color.Gray)
                    Text(text = item.size, fontSize = 13.sp, color = mainBlue)
                }

                Spacer(modifier = Modifier.height(8.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(onClick = { viewModel.decrementQuantity(item) }) {
                        Icon(Icons.Default.Remove, contentDescription = null)
                    }
                    Text("${item.quantity}", modifier = Modifier.padding(horizontal = 8.dp))

                    IconButton(
                        onClick = { viewModel.incrementQuantity(item) },
                        enabled = item.quantity < 10
                    ) {
                        Icon(Icons.Default.Add, contentDescription = null, tint = if (item.quantity < 10) Color.Black else Color.Gray)
                    }

                }
            }

            Text(
                text = "${"%.2f".format(item.price * item.quantity)}€",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = mainBlue,
                modifier = Modifier
                    .align(Alignment.Bottom)
                    .padding(end = 12.dp, bottom = 12.dp)
            )
        }
    }
}

