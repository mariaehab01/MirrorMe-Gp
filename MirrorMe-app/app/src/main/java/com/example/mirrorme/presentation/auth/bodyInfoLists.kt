package com.example.mirrorme.presentation.auth

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.mirrorme.R

@Composable
fun getFemaleBodies(): List<BodyInfoItem> = listOf(
    BodyInfoItem.ImageItem(painterResource(R.drawable.f_body1), 50.dp, 80.dp),
    BodyInfoItem.ImageItem(painterResource(R.drawable.f_body2), 50.dp, 80.dp),
    BodyInfoItem.ImageItem(painterResource(R.drawable.f_body3), 50.dp, 80.dp),
    BodyInfoItem.ImageItem(painterResource(R.drawable.f_body4), 50.dp, 80.dp),
    BodyInfoItem.ImageItem(painterResource(R.drawable.f_body5), 50.dp, 80.dp)
)

@Composable
fun getMaleBodies(): List<BodyInfoItem> = listOf(
    BodyInfoItem.ImageItem(painterResource(R.drawable.m_body1), 50.dp, 80.dp),
    BodyInfoItem.ImageItem(painterResource(R.drawable.m_body2), 50.dp, 80.dp),
    BodyInfoItem.ImageItem(painterResource(R.drawable.m_body3), 50.dp, 80.dp),
    BodyInfoItem.ImageItem(painterResource(R.drawable.m_body4), 50.dp, 80.dp),
    BodyInfoItem.ImageItem(painterResource(R.drawable.m_body5), 50.dp, 80.dp)
)

fun getSkinTones(): List<BodyInfoItem> = listOf(
    BodyInfoItem.ColorItem(Color(0xFFF8D0C6)),
    BodyInfoItem.ColorItem(Color(0xFFFFE0B2)),
    BodyInfoItem.ColorItem(Color(0xFFF8BF8D)),
    BodyInfoItem.ColorItem(Color(0xFFD7A17E)),
    BodyInfoItem.ColorItem(Color(0xFFB98064)),
    BodyInfoItem.ColorItem(Color(0xFF8D5A4A)),
    BodyInfoItem.ColorItem(Color(0xFF5D4037)),
    BodyInfoItem.ColorItem(Color(0xFF3E2723))
)

@Composable
fun getFemaleBodyMap(): Map<BodyInfoItem, String> {
    val items = getFemaleBodies()
    return items.zip(listOf("Hourglass", "Round", "Inverted-Triangle", "Rectangle", "Pear")).associate { (item, name) ->
        item to name
    }
}

@Composable
fun getMaleBodyMap(): Map<BodyInfoItem, String> {
    val items = getMaleBodies()
    return items.zip(listOf("Hourglass", "Round", "Inverted-Triangle", "Rectangle", "Pear")).associate { (item, name) ->
        item to name
    }
}

fun getSkinToneMap(): Map<BodyInfoItem, String> {
    val items = getSkinTones()
    return items.zip(listOf("Fair1", "Fair2", "Light", "MediumLight", "Medium", "MediumDark", "Dark", "Deep")).associate { (item, name) ->
        item to name
    }
}