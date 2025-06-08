package com.example.mirrorme.presentation.auth.composes

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mirrorme.ui.theme.grayPlaceholders
import com.example.mirrorme.ui.theme.lightBlue

@Composable
fun RoundedTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    icon: ImageVector
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = {
            Text(
                text = placeholder,
                color = grayPlaceholders,
            )
        },
        leadingIcon = {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = grayPlaceholders,
                modifier = Modifier
                    .width(60.dp)
                    .height(20.dp) // Adjust icon size if needed
            )
        },
        singleLine = true,
        modifier = Modifier
            .width(302.dp)
            .height(52.dp)
            .shadow(6.dp, RoundedCornerShape(50)) // Add subtle shadow
            .background(Color.White, RoundedCornerShape(50))
            .border(
                width = 1.5.dp,
                color = lightBlue,
                shape = RoundedCornerShape(50)
            ), // White background with rounded corners
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
            disabledContainerColor = Color.White,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        ),
        shape = RoundedCornerShape(50)
    )
}

//preview
@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
fun RoundedTextFieldPreview() {
    RoundedTextField(
        value = "",
        onValueChange = {},
        placeholder = "Enter your email",
        icon = Icons.Default.Email
    )
}