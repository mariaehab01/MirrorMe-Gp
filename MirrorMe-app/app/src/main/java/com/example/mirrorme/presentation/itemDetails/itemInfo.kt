package com.example.mirrorme.presentation.itemDetails

import ProductViewModel
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.mirrorme.presentation.itemDetails.composes.ActionButtons
import com.example.mirrorme.presentation.itemDetails.composes.CodeAndReviews
import com.example.mirrorme.presentation.itemDetails.composes.ColorSizeQuantitySection
import com.example.mirrorme.presentation.itemDetails.composes.NameAndStars
import com.example.mirrorme.presentation.itemDetails.composes.OutfitContainer
import com.example.mirrorme.presentation.itemDetails.composes.ProductDescriptionSection
import com.example.mirrorme.presentation.itemDetails.composes.ProductImage
import com.example.mirrorme.presentation.itemDetails.composes.ScrollableRowWithArrows
import com.example.mirrorme.presentation.itemDetails.composes.TopBar
import com.example.mirrorme.ui.theme.*

@Composable
fun ItemInfoScreen(productId: String, navController: NavHostController) {
    val viewModel = remember { ProductViewModel() }
    val product = viewModel.productUiState
    val isLoading = viewModel.isLoading
    val errorMessage = viewModel.errorMessage

    var rating by rememberSaveable { mutableStateOf(0) }
    var selectedColorIndex by remember { mutableStateOf(0) }
    var selectedSizeIndex by remember { mutableStateOf(0) }
    var quantity by remember { mutableStateOf(1) }
    var colorScrollStart by remember { mutableStateOf(0) }
    var sizeScrollStart by remember { mutableStateOf(0) }
    var similarScrollStart by remember { mutableStateOf(0) }
    var suggestionsScrollStart by remember { mutableStateOf(0) }
    var showOutfitContainer by remember { mutableStateOf(false) }
    val scrollState = rememberScrollState()

    val colors = listOf(Color.Red, Color.White, Color.Gray, Color.Green, Color.Blue, Color.Yellow)
    val sizes = listOf("S", "M", "L", "XL", "XXL", "3XL", "4XL")

    LaunchedEffect(productId) {
        viewModel.loadProduct(productId)
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
                    TopBar(navController)

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
                    CodeAndReviews(product)
                    Spacer(modifier = Modifier.height(4.dp))

                    // Price
                    Text("${product.price} LE",
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
                        onQuantityChange = { newQuantity -> quantity = newQuantity },
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
                        onTryOn = { /* Handle Add to Cart */ },
                        onAddToBag = { /* Handle Buy Now */ },
                        onGenerateOutfit = { showOutfitContainer = true }
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // -------- Similar Items Scrollable --------
                    ScrollableRowWithArrows(
                        title = "Similar Items",
                        itemCount = colors.size,
                        scrollStart = similarScrollStart,
                        onScrollChange = { similarScrollStart = it },
                        boxColor = Color.LightGray
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    // -------- Suggestions Scrollable --------
                    ScrollableRowWithArrows(
                        title = "Suggestions",
                        itemCount = sizes.size,
                        scrollStart = suggestionsScrollStart,
                        onScrollChange = { suggestionsScrollStart = it },
                        boxColor = Color.Gray)

                    // -------- Outfit Container --------
                    if (showOutfitContainer) {
                        LaunchedEffect(showOutfitContainer) {
                            scrollState.animateScrollTo(scrollState.maxValue)
                        }
                        OutfitContainer()
                    }

                    Spacer(modifier = Modifier.height(40.dp))  // Bottom space
                }
            }
        }
    }
}
