package com.example.mirrorme.presentation.cart

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowLeft
import androidx.compose.material.icons.filled.ArrowRight
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.mirrorme.ui.theme.mainBlue
import com.example.mirrorme.ui.theme.mainPink
import com.example.mirrorme.ui.theme.semiWhite
import com.example.mirrorme.presentation.cart.composes.CartItemCard
import androidx.compose.material3.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.mirrorme.presentation.itemDetails.composes.TopBar
import com.example.mirrorme.presentation.ratingsAndReviews.RatingsAndReviewsScreen
import com.example.mirrorme.ui.theme.MirrorMeTheme
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction


@Composable
fun CartScreen(navController: NavHostController, cartViewModel: CartViewModel) {

//    val viewModel: CartViewModel = viewModel()

    val focusManager = LocalFocusManager.current // 👈 Fix added here
    var promoCode by remember { mutableStateOf("") } // 👈 Needed to store user input
    val cartItems by cartViewModel.cartItems.collectAsState()
    val totalPrice by cartViewModel.totalPrice.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF4F4F8))
            .padding(horizontal = 16.dp)
    ) {
        // Top Bar
        TopBar(navController, "Cart")

        Spacer(modifier = Modifier.height(8.dp))

        if (cartItems.isEmpty()) {
            // Show "Cart is empty" message
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Your cart is empty",
                    color = Color.Gray,
                    fontSize = 18.sp
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(cartItems) { item ->
                    CartItemCard(item = item, viewModel = cartViewModel)
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Promo Code Field
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(30.dp))
                .background(Color.White)
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                value = promoCode,
                onValueChange = {promoCode = it },
                placeholder = { Text("Enter your promo code") },
                modifier = Modifier.weight(1f),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                ),
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(
                    onDone = {
                        // ✅ You can validate or apply promo here
                        focusManager.clearFocus() // ✅ Hide keyboard
                    }
                )
            )
            IconButton(onClick = { /* Apply promo */ }, modifier = Modifier
                .size(40.dp)
                .background(mainBlue, shape = RoundedCornerShape(30.dp))) {
                Icon(Icons.Default.ArrowForward, contentDescription = null, tint = Color.White)
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Total Amount
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Total amount:", fontSize = 16.sp, color = Color.Gray)
            Text("${'$'}${"%.2f".format(totalPrice)}", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = mainBlue)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Checkout Button
        Button(
            onClick = { /* Handle Checkout */ },
            colors = ButtonDefaults.buttonColors(containerColor = mainPink),
            shape = RoundedCornerShape(30.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
        ) {
            Text("Checkout", color = Color.White, fontSize = 18.sp)
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CartScreenPreview() {
    MirrorMeTheme {
        CartScreen(
            navController = rememberNavController(),
            cartViewModel = viewModel<CartViewModel>()
        )
    }
}



