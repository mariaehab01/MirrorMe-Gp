package com.example.mirrorme.presentation.auth

import BodyInfoScroller
import BodyMeasureField
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.material.icons.rounded.MailOutline
import androidx.compose.material.icons.rounded.Phone
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mirrorme.presentation.auth.composes.PageHeader
import com.example.mirrorme.presentation.auth.composes.PasswordField
import com.example.mirrorme.presentation.auth.composes.RoundedTextField
import com.example.mirrorme.presentation.auth.composes.fieldsContainer
import com.example.mirrorme.ui.theme.MirrorMeTheme
import com.example.mirrorme.ui.theme.gradient
import com.example.mirrorme.ui.theme.mainBlue
import com.example.mirrorme.ui.theme.mainPink


class BodyInfo : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MirrorMeTheme {
                BodyInfoContent()
            }
        }
    }
}

@Composable
fun BodyInfoContent() {
//    val bodyShapes = listOf(
//        BodyInfoItem.ImageItem(painterResource(id = R.drawable.body_shape_1)),
//        BodyInfoItem.ImageItem(painterResource(id = R.drawable.body_shape_2)),
//        BodyInfoItem.ImageItem(painterResource(id = R.drawable.body_shape_3)),
//        BodyInfoItem.ImageItem(painterResource(id = R.drawable.body_shape_4)),
//        BodyInfoItem.ImageItem(painterResource(id = R.drawable.body_shape_5))
//    )

    val skinTones = listOf(
        BodyInfoItem.ColorItem(Color(0xFFFFE0B2)),
        BodyInfoItem.ColorItem(Color(0xFFD7A17E)),
        BodyInfoItem.ColorItem(Color(0xFFB98064)),
        BodyInfoItem.ColorItem(Color(0xFF8D5A4A)),
        BodyInfoItem.ColorItem(Color(0xFF5D4037)),
        BodyInfoItem.ColorItem(Color(0xFF3E2723))
    )

    var selectedShapeIndex by remember { mutableStateOf(0) }
    var selectedToneIndex by remember { mutableStateOf(0) }
    var height by remember { mutableStateOf("165") }
    var weight by remember { mutableStateOf("60") }

    Box {
        PageHeader(
            line1 = "Wooh,", line2 = "Almost There...",
            modifier = Modifier
                .fillMaxSize()
                .background(gradient)
                .padding(top = 70.dp, start = 36.dp),
            contentAlignment = Alignment.TopStart
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            fieldsContainer(
                "Body Info",
                0.7f,
                modifier = Modifier.padding(start = 36.dp, end = 36.dp, top = 190.dp)
            ) {
                Spacer(modifier = Modifier.height(12.dp))

                BodyMeasureField(
                    title = "Height",
                    unit = "cm",
                    initialValue = height,
                    onValueChange = { height = it }
                )

                Spacer(modifier = Modifier.height(10.dp))

                BodyMeasureField(
                    title = "Weight",
                    unit = "Kg",
                    initialValue = weight,
                    onValueChange = { weight = it }
                )

                Spacer(modifier = Modifier.height(16.dp))

                Spacer(modifier = Modifier.height(16.dp))

                BodyInfoScroller(
                    title = "Skin Tone",
                    items = skinTones,
                    itemSize = 40.dp,
                    itemHeight = 40.dp,
                    shape = CircleShape
                ) { selectedToneIndex = it }
            }

            Spacer(modifier = Modifier.height(30.dp))

            Button(
                onClick = { /* TODO */ },
                colors = ButtonDefaults.buttonColors(containerColor = mainPink),
                shape = RoundedCornerShape(50),
                modifier = Modifier
                    .width(200.dp)
                    .height(50.dp)
            ) {
                Text("Sign Up", fontWeight = FontWeight.Bold, color = Color.White)
            }

            Spacer(modifier = Modifier.height(20.dp))
        }

    }
}


@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
fun BodyInfoContentPreview() {
    MirrorMeTheme {
        BodyInfoContent()
    }
}