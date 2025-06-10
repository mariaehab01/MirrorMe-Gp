package com.example.mirrorme.presentation.auth.composes

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mirrorme.ui.theme.mainBlue

@Composable
fun BodyMeasureField(
    title: String,
    unit: String,
    value: String,
    onValueChange: (String) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(vertical = 8.dp)
            .fillMaxWidth()
    ) {
        Text(
            text = title,
            fontSize = 22.sp,
            fontWeight = FontWeight.Medium,
            color = mainBlue,
            modifier = Modifier.width(90.dp)
        )

        Spacer(modifier = Modifier.width(65.dp))

        TextField(
            value = value,
            onValueChange = {
                if (it.all { char -> char.isDigit() }) {
                    onValueChange(it)
                }
            },
            singleLine = true,
            placeholder = {
                val example = if (unit == "cm") "ex: 170" else "ex: 70"
                Text(example, fontSize = 12.sp, color = Color.Gray)
            },
            modifier = Modifier
                .width(90.dp)
                .height(50.dp), // standard TextField height
            textStyle = LocalTextStyle.current.copy(
                fontSize = 14.sp,
                color = mainBlue // ensure text is visible
            ),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White
            ),
            shape = RoundedCornerShape(8.dp)
        )

        Spacer(modifier = Modifier.width(8.dp))

        Text(
            text = unit,
            fontSize = 14.sp,
            color = Color(0xFF0A2A38)
        )
    }
}
