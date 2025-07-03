package com.example.mirrorme.presentation.ratingsAndReviews

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.example.mirrorme.presentation.home.composes.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.mirrorme.presentation.ratingsAndReviews.composes.RatingSummary
import com.example.mirrorme.presentation.ratingsAndReviews.composes.ReviewItem
import com.example.mirrorme.presentation.ratingsAndReviews.composes.WriteReviewBottomSheet
import com.example.mirrorme.ui.theme.MirrorMeTheme
import com.example.mirrorme.presentation.itemDetails.composes.TopBar
import com.example.mirrorme.ui.theme.mainPink
import com.example.mirrorme.ui.theme.off_white
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import com.example.mirrorme.domain.model.ReviewData

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RatingsAndReviewsScreen(
    navController: NavHostController
) {
    val showBottomSheet = remember { mutableStateOf(false) }

    val reviews = listOf(
        ReviewData("Esther Howard", "2 mins ago", 5, "I absolutely love this item! The virtual try-on made finding the perfect size effortless with a realistic preview."),
        ReviewData("Marvin McKinney", "3 weeks ago", 4, "Really stylish and comfortable! The virtual try-on helped me pick the right size..."),
        ReviewData("Arlene McCoy", "1 month ago", 3, "Average item but not perfect. The try-on helped with sizing, but the color was slightly off...")
    )

    Box(modifier = Modifier.fillMaxSize().background(off_white)) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            TopBar(navController = navController, "Ratings & Reviews")

            Spacer(modifier = Modifier.height(12.dp))

            RatingSummary()

            Spacer(modifier = Modifier.height(16.dp))

            // Scrollable reviews only
            val scrollState = rememberScrollState()
            Column(
                modifier = Modifier
                    .weight(1f) // take remaining height between top & button
                    .verticalScroll(scrollState)
            ) {
                reviews.forEach { review ->
                    ReviewItem(
                        name = review.name,
                        time = review.time,
                        stars = review.stars,
                        comment = review.comment
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Fixed "Write Review" button
            Button(
                onClick = { showBottomSheet.value = true },
                colors = ButtonDefaults.buttonColors(containerColor = mainPink),
                modifier = Modifier
                    .padding(bottom = 40.dp)
                    .height(60.dp)
                    .width(285.dp)
                    .align(Alignment.CenterHorizontally)
                    .clip(RoundedCornerShape(50))
            ) {
                Text("Write Review", color = Color.White, fontSize = 20.sp)
                Spacer(modifier = Modifier.width(8.dp))
                Icon(Icons.Default.Edit, contentDescription = "Send", tint = Color.White)
            }
        }

        if (showBottomSheet.value) {
            WriteReviewBottomSheet { showBottomSheet.value = false }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun RatingsAndReviewsScreenPreview() {
    MirrorMeTheme {
        RatingsAndReviewsScreen(navController = rememberNavController())
    }
}

