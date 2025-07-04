package com.example.mirrorme.presentation.home.composes

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.mirrorme.R
import com.example.mirrorme.ui.theme.lightBlue
import com.example.mirrorme.ui.theme.mainPink
import com.example.mirrorme.ui.theme.mainBlue

@Composable
fun TopBarSection(onMenuClick: () -> Unit, navController: NavHostController) {
    val bottomRoundedShape = RoundedCornerShape(
        topStart = 0.dp,
        topEnd = 0.dp,
        bottomStart = 16.dp,
        bottomEnd = 16.dp
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(2.dp, shape = bottomRoundedShape)
            .padding(12.dp)
            .height(40.dp)
            .clip(bottomRoundedShape),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onMenuClick) {
            Icon(imageVector = Icons.Filled.Menu,
                contentDescription = "Menu",
                tint = mainPink,
                modifier = Modifier.size(28.dp)
            )
        }

        Text("MirrorMe", fontSize = 22.sp, color = mainBlue)

        IconButton(onClick = {
            //navigate to cart screen
            navController.navigate("cart")
        }) {
                Icon(painterResource(id = R.drawable.cart),
                    contentDescription = "Cart",
                    tint = Color.Unspecified,
                    modifier = Modifier.size(30.dp)
                )
        }
    }
}
