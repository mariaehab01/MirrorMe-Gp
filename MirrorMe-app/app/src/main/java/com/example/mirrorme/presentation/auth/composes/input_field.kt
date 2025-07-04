package com.example.mirrorme.presentation.auth.composes

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mirrorme.R
import com.example.mirrorme.ui.theme.grayPlaceholders
import com.example.mirrorme.ui.theme.lightBlue

@Composable
fun RoundedTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    icon: @Composable (() -> Unit)? = null,
    errorMessage: String? = null
) {
    val defaultIcon: @Composable () -> Unit = {
        Icon(
            painter = painterResource(id = R.drawable.email),
            contentDescription = "Lock Icon",
            modifier = Modifier
                .size(36.dp)
                .padding(start = 16.dp)
        )
    }
    Column {
        TextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = {
                Text(
                    text = placeholder,
                    color = grayPlaceholders,
                    fontSize = 14.sp,
                )
            },
            leadingIcon = icon ?: defaultIcon,
            singleLine = true,
            modifier = Modifier
                .width(302.dp)
                .height(52.dp)
                .shadow(6.dp, RoundedCornerShape(50))
                .background(Color.White, RoundedCornerShape(50))
                .border(
                    width = 1.5.dp,
                    color = if (errorMessage != null) Color.Red else lightBlue,
                    shape = RoundedCornerShape(50)
                ),
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
        if (errorMessage != null) {
            Text(
                text = errorMessage,
                color = Color.Red,
                fontSize = 12.sp,
                modifier = Modifier.padding(start = 16.dp, top = 4.dp)
            )
        }
    }
}



//preview
@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
fun RoundedTextFieldPreview() {
    RoundedTextField(
        value = "",
        onValueChange = {},
        placeholder = "Enter your email",
    )
}