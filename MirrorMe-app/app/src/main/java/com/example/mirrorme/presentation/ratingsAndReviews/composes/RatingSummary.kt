package com.example.mirrorme.presentation.ratingsAndReviews.composes

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.example.mirrorme.presentation.home.composes.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.mirrorme.ui.theme.mainBlue
import com.example.mirrorme.ui.theme.mainPink
import com.example.mirrorme.ui.theme.semiWhite


@Composable
fun RatingSummary() {
    Card(
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(2.dp, mainBlue),
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = semiWhite)
    ) {
        Row(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(60.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                (5 downTo 1).forEach { star ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "$star",
                            color = mainBlue,
                            modifier = Modifier.width(20.dp)
                        )
                        Icon(Icons.Default.Star, contentDescription = null, tint = mainPink, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Box(
                            modifier = Modifier
                                .height(8.dp)
                                .width((30 * star).dp)
                                .background(mainBlue, RoundedCornerShape(8.dp))
                        )
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                }
            }


            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("4.0", style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    repeat(4) {
                        Icon(Icons.Default.Star, null, tint = mainPink, modifier = Modifier.size(16.dp))
                    }
                    Icon(Icons.Default.StarBorder, null, tint = mainPink, modifier = Modifier.size(16.dp))
                }
                Text("42 Reviews", color = mainBlue, fontSize = 16.sp)
            }
        }
    }
}
