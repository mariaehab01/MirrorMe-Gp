package com.example.mirrorme.presentation.auth.composes

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mirrorme.ui.theme.grayPlaceholders
import com.example.mirrorme.ui.theme.lightBlue
import com.example.mirrorme.R

@Composable
fun PasswordField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    icon: @Composable (() -> Unit)? = null,
    errorMessage: String? = null,
    confirmPassword: String? = null,
    confirmErrorMessage: String? = null
) {
    var passwordVisible by remember { mutableStateOf(false) }
    val defaultIcon: @Composable () -> Unit = {
        Icon(
            painter = painterResource(id = R.drawable.lock),
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
            trailingIcon = {
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(
                        painter = painterResource(id = if (passwordVisible) R.drawable.eye_opened else R.drawable.eye_closed),
                        contentDescription = if (passwordVisible) "Hide password" else "Show password",
                        tint = grayPlaceholders,
                        modifier = Modifier
                            .size(36.dp)
                            .padding(end = 16.dp)
                    )
                }
            },
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            singleLine = true,
            modifier = Modifier
                .width(302.dp)
                .height(52.dp)
                .shadow(6.dp, RoundedCornerShape(50))
                .background(Color.White, RoundedCornerShape(50))
                .border(
                    width = 1.5.dp,
                    color = if (errorMessage != null || confirmErrorMessage != null) Color.Red else lightBlue,
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
        if (confirmErrorMessage != null) {
            Text(
                text = confirmErrorMessage,
                color = Color.Red,
                fontSize = 12.sp,
                modifier = Modifier.padding(start = 16.dp, top = 4.dp)
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
fun PasswordFieldPreview() {
    PasswordField(
        value = "",
        onValueChange = {},
        placeholder = "Enter your password"
    )
}
