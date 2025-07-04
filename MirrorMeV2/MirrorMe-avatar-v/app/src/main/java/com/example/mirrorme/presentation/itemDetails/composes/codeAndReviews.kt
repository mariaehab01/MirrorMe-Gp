package com.example.mirrorme.presentation.itemDetails.composes

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.mirrorme.domain.model.Product
import com.example.mirrorme.ui.theme.itemsBlue

@Composable
fun CodeAndReviews(product: Product, navController: NavHostController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 30.dp, vertical = 2.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text("Product code: ${product.id.take(8)}", fontSize = 14.sp, color = itemsBlue)
        Text(
            "24 Reviews",
            fontSize = 16.sp,
            color = itemsBlue,
            textDecoration = TextDecoration.Underline,
            modifier = Modifier.clickable {
                navController.navigate("ratingsAndReviews")
            }
        )
    }
}