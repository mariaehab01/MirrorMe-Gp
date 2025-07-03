package com.example.mirrorme.presentation.itemDetails

import itemViewModel
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.mirrorme.data.tryOn.colorList
import com.example.mirrorme.domain.model.CartItem
//import com.example.mirrorme.domain.model.CartItem
import com.example.mirrorme.presentation.cameraTryOn.CameraTryOnActivity
import com.example.mirrorme.presentation.cart.CartViewModel
//import com.example.mirrorme.presentation.cart.CartViewModel
import com.example.mirrorme.presentation.itemDetails.composes.ActionButtons
import com.example.mirrorme.presentation.itemDetails.composes.CodeAndReviews
import com.example.mirrorme.presentation.itemDetails.composes.ColorSizeQuantitySection
import com.example.mirrorme.presentation.itemDetails.composes.NameAndStars
import com.example.mirrorme.presentation.itemDetails.composes.ProductDescriptionSection
import com.example.mirrorme.presentation.itemDetails.composes.ProductImage
import com.example.mirrorme.presentation.itemDetails.composes.ScrollableRowWithArrows
import com.example.mirrorme.presentation.itemDetails.composes.TopBar
import com.example.mirrorme.ui.theme.*
import androidx.compose.ui.graphics.Color as ComposeColor

val colors = colorList.map { ComposeColor(it) }


@Composable
fun ItemInfoScreen(productId: Int, navController: NavHostController, cartViewModel: CartViewModel) {
    Log.d("ItemInfoScreen", "Calling loadSimilarItems with $productId")

    val context = LocalContext.current
    val viewModel = remember { itemViewModel() }
    val product = viewModel.productUiState
    val isLoading = viewModel.isLoading
    val errorMessage = viewModel.errorMessage
    val maxQuantity = 10

    var rating by rememberSaveable { mutableStateOf(0) }
    var selectedColorIndex by remember { mutableStateOf(0) }
    var selectedSizeIndex by remember { mutableStateOf(0) }
    var quantity by remember { mutableStateOf(1) }
    var colorScrollStart by remember { mutableStateOf(0) }
    var sizeScrollStart by remember { mutableStateOf(0) }
    var similarScrollStart by remember { mutableStateOf(0) }
    var showOutfitContainer by remember { mutableStateOf(false) }
    val scrollState = rememberScrollState()

    val colors = colorList.map { ComposeColor(it) }
    val sizes = listOf("S", "M", "L", "XL", "XXL", "3XL", "4XL")

    val finedProductId = productId - 1 // Assuming productId is 0-based index

    LaunchedEffect(productId) {
        Log.d("ItemInfoScreen", "Calling loadSimilarItems with $productId")

        viewModel.loadProduct(productId)
        viewModel.loadSimilarItems(finedProductId) // Assuming productId is 0-based index
        viewModel.loadCompatibleItems(finedProductId)
    }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(off_white),
        contentAlignment = Alignment.Center
    ) {
        when {
            isLoading -> CircularProgressIndicator()

            errorMessage != null -> Text(text = "Error: $errorMessage")

            product != null -> {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(scrollState)

                ) {
                    // Top Bar
                    TopBar(navController, "Item Info")

                    // Product Image
                    ProductImage(product.imageUrl)

                    Spacer(modifier = Modifier.height(8.dp))

                    // Name + Stars
                    NameAndStars(
                        product = product,
                        rating = rating,
                        onRatingChange = { newRating -> rating = newRating }
                    )

                    // Product Code + Reviews
                    CodeAndReviews(product, navController)
                    Spacer(modifier = Modifier.height(4.dp))

                    // Price
                    Text("${"%.2f".format(product.price)} LE",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.W700,
                        color = itemsBlue, modifier =
                            Modifier
                                .padding(horizontal = 30.dp)
                                .align(Alignment.Start))

                    Spacer(modifier = Modifier.height(12.dp))

                    // -------- Color + Size + Available + Quantity --------
                    ColorSizeQuantitySection(
                        colors = colors,
                        sizes = sizes,
                        selectedColorIndex = selectedColorIndex,
                        selectedSizeIndex = selectedSizeIndex,
                        quantity = quantity,
                        onColorSelect = { index -> selectedColorIndex = index },
                        onSizeSelect = { index -> selectedSizeIndex = index },
                        onQuantityChange = { newQuantity ->
                            if (newQuantity in 1..maxQuantity) {
                                quantity = newQuantity
                            }
                        },
                        colorScrollStart = colorScrollStart,
                        sizeScrollStart = sizeScrollStart,
                        onColorScrollChange = { newStart -> colorScrollStart = newStart },
                        onSizeScrollChange = { newStart -> sizeScrollStart = newStart }
                    )
                    Spacer(modifier = Modifier.height(20.dp))

                    // -------- Product Description Section --------
                    ProductDescriptionSection()
                    Spacer(modifier = Modifier.height(12.dp))

                    // -------- Buttons Row --------
                    ActionButtons(
                        onTryOn = {val intent = Intent(context, CameraTryOnActivity::class.java)
                            intent.putExtra("MODEL_URL", product.objectUrl)
                            context.startActivity(intent)
                                  },
                        onAddToBag = {
                            val selectedProduct = product

                            val existingItem = cartViewModel.cartItems.value.find {
                                it.productId == selectedProduct.ml_id &&
                                        it.color == colors[selectedColorIndex].toString() &&
                                        it.size == sizes[selectedSizeIndex]
                            }

                            val currentQuantityInCart = existingItem?.quantity ?: 0
                            val totalRequested = currentQuantityInCart + quantity

                            if (totalRequested > maxQuantity) {
                                Toast.makeText(context, "Maximum quantity reached", Toast.LENGTH_SHORT).show()
                            } else {
                                val cartItem = CartItem(
                                    productId = selectedProduct.ml_id,
                                    name = selectedProduct.name,
                                    imageRes = selectedProduct.imageUrl,
                                    color = colors[selectedColorIndex].toString(),
                                    size = sizes[selectedSizeIndex],
                                    price = selectedProduct.price,
                                    quantity = quantity
                                )
                                cartViewModel.addItem(cartItem)
                                Toast.makeText(context, "${selectedProduct.name} added to bag", Toast.LENGTH_SHORT).show()
                            }
                        },
                        onGenerateOutfit = {
                            viewModel.loadOutfitItems(finedProductId)
                            showOutfitContainer = true }
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // -------- Similar Items Scrollable --------
                    ScrollableRowWithArrows(
                        title = "Similarities",
                        products = viewModel.similarProducts,
                        scrollStart = similarScrollStart,
                        onScrollChange = { similarScrollStart = it },
                        boxColor = Color.LightGray,
                        emptyMessage = "Similar items will be ready soon"
                    )
                    Log.d("ItemInfoScreennnnn", "Calling similaritylist done: ${viewModel.similarProducts} items")


                    Spacer(modifier = Modifier.height(16.dp))

//                     -------- Suggestions Scrollable --------
                    ScrollableRowWithArrows(
                        title = "Suggestions",
                        products = viewModel.similarProducts,
                        scrollStart = similarScrollStart,
                        onScrollChange = { similarScrollStart = it },
                        boxColor = Color.LightGray,
                        emptyMessage = "Compatible items will be ready soon"
                    )
                    Log.d("ItemInfoScreennnnn", "Calling compatiblitylist done: ${viewModel.similarProducts} items")

                    Spacer(modifier = Modifier.height(16.dp))

                    // -------- Outfit Container --------
                    if (showOutfitContainer) {
                        LaunchedEffect(showOutfitContainer) {
                            scrollState.animateScrollTo(scrollState.maxValue)
                        }

                        ScrollableRowWithArrows(
                            title = "Generated Outfit",
                            products = viewModel.outfitProducts,
                            scrollStart = 0,
                            onScrollChange = { /* Optional: Implement outfit scroll start state */ },
                            boxColor = Color.Gray,
                            emptyMessage = "No generated outfit found",
                            onItemClick = { selectedProduct ->
                                // Optional: Handle outfit item click
                            }
                        )
                    }

                    Spacer(modifier = Modifier.height(40.dp))  // Bottom space
                }
            }
        }
    }
}


