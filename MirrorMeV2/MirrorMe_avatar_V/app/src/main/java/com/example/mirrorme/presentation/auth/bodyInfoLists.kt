package com.example.mirrorme.presentation.auth

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.mirrorme.R
import com.example.mirrorme.presentation.auth.composes.BodyInfoItem

@Composable
fun getFemaleBodies(): List<BodyInfoItem> = listOf(
    BodyInfoItem.ImageItem(painterResource(R.drawable.f_body1), "Hourglass", 50.dp, 80.dp),
    BodyInfoItem.ImageItem(painterResource(R.drawable.f_body2), "Round", 50.dp, 80.dp),
    BodyInfoItem.ImageItem(painterResource(R.drawable.f_body3), "Inverted-Triangle", 50.dp, 80.dp),
    BodyInfoItem.ImageItem(painterResource(R.drawable.f_body4), "Rectangle", 50.dp, 80.dp),
    BodyInfoItem.ImageItem(painterResource(R.drawable.f_body5), "Pear", 50.dp, 80.dp)
)

@Composable
fun getMaleBodies(): List<BodyInfoItem> = listOf(
    BodyInfoItem.ImageItem(painterResource(R.drawable.m_body1), "Hourglass", 50.dp, 80.dp),
    BodyInfoItem.ImageItem(painterResource(R.drawable.m_body2), "Round", 50.dp, 80.dp),
    BodyInfoItem.ImageItem(painterResource(R.drawable.m_body3), "Inverted-Triangle", 50.dp, 80.dp),
    BodyInfoItem.ImageItem(painterResource(R.drawable.m_body4), "Rectangle", 50.dp, 80.dp),
    BodyInfoItem.ImageItem(painterResource(R.drawable.m_body5), "Pear", 50.dp, 80.dp)
)

fun getSkinTones(): List<BodyInfoItem> = listOf(
    BodyInfoItem.ColorItem(Color(0xFFF8D0C6), "Fair1"),
    BodyInfoItem.ColorItem(Color(0xFFFFE0B2), "Fair2"),
    BodyInfoItem.ColorItem(Color(0xFFF8BF8D), "Light"),
    BodyInfoItem.ColorItem(Color(0xFFD7A17E), "MediumLight"),
    BodyInfoItem.ColorItem(Color(0xFFB98064), "Medium"),
    BodyInfoItem.ColorItem(Color(0xFF8D5A4A), "MediumDark"),
    BodyInfoItem.ColorItem(Color(0xFF5D4037), "Dark"),
    BodyInfoItem.ColorItem(Color(0xFF3E2723), "Deep")
)
