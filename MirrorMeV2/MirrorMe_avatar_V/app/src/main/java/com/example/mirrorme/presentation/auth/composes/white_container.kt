package com.example.mirrorme.presentation.auth.composes

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mirrorme.ui.theme.lightBlue

@Composable
fun fieldsContainer(
    title: String,
    heightFraction: Float,
    modifier: Modifier = Modifier,
    contentAlignment: Alignment = Alignment.TopCenter,
    content: @Composable () -> Unit // allow composable content
) {
    Box(
        modifier = modifier
            .width(341.dp)
            .fillMaxHeight(heightFraction)
            .background(
                color = Color.White.copy(alpha = 0.6f),
                shape = androidx.compose.foundation.shape.RoundedCornerShape(20.dp)
            )
            .border(
                width = 1.dp,
                color = Color.White,
                shape = androidx.compose.foundation.shape.RoundedCornerShape(20.dp)
            ),
        contentAlignment = contentAlignment
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 18.dp, vertical = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = title,
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                color = lightBlue,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(26.dp))

            content() //Insert the user-defined content
        }
    }
}


//preview
@Preview(showBackground = true)
@Composable
fun PreviewFieldsContainer() {
    fieldsContainer("sign up", 0.6f) {

    }
}