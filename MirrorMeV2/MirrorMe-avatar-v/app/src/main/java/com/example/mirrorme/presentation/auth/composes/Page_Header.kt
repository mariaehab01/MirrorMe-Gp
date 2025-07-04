package com.example.mirrorme.presentation.auth.composes

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.example.mirrorme.ui.theme.semiWhite

@Composable
fun PageHeader(
    line1: String,
    line2: String,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        Text(
            text = line1,
            style = MaterialTheme.typography.headlineMedium,
            color = semiWhite,
            fontSize = 32.sp,
            textAlign = TextAlign.Start,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = line2,
            style = MaterialTheme.typography.bodyMedium,
            color = semiWhite,
            fontSize = 32.sp,
            textAlign = TextAlign.Start,
            fontWeight = FontWeight.Bold
        )
    }
}
