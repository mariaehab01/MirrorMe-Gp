package com.example.mirrorme.presentation.home.composes

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Help
import androidx.compose.material.icons.filled.HelpOutline
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PersonOutline
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.mirrorme.ui.theme.lightBlue
import com.example.mirrorme.ui.theme.mainPink
import com.example.mirrorme.ui.theme.off_white

@Composable
fun BottomNavBar(selectedPage: String) {
    BottomAppBar(
        containerColor = off_white,
        contentColor = lightBlue,
        modifier = Modifier
            .shadow(16.dp, RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp), clip = false)
            .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
            .fillMaxWidth()
            .height(70.dp)
    ) {
        val items = listOf(
            Triple("home", Icons.Outlined.Home, "Home"),
            Triple("favorites", Icons.Filled.FavoriteBorder, "Favorites"),
            Triple("help", Icons.Filled.HelpOutline, "Help"),
            Triple("profile", Icons.Filled.PersonOutline, "Profile")
        )

        items.forEach { (page, icon, label) ->
            NavigationBarItem(
                selected = selectedPage == page,
                onClick = {},
                icon = { Icon(icon, contentDescription = label) },
                label = { Text(label) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = mainPink,
                    selectedTextColor = mainPink,
                    unselectedIconColor = lightBlue,
                    unselectedTextColor = lightBlue,
                    indicatorColor = Color.Transparent
                )
            )
        }
    }
}

