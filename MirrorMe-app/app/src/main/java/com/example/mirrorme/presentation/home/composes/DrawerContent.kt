package com.example.mirrorme.presentation.home.composes

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.Man
import androidx.compose.material.icons.outlined.Woman
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.mirrorme.R
import com.example.mirrorme.presentation.onboarding.FirstScreen
import com.example.mirrorme.ui.theme.lightBlue
import com.example.mirrorme.ui.theme.lightTeal
import com.example.mirrorme.ui.theme.mainBlue
import com.example.mirrorme.ui.theme.mainPink
import com.example.mirrorme.ui.theme.off_white
import com.example.mirrorme.ui.theme.popColor

@Composable
fun AppDrawerContent(onLogoutClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(off_white)
            .padding(horizontal = 16.dp)
    ) {
        // Logo
        Box(
            modifier = Modifier
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.mirrorme_bg),
                contentDescription = "Logo",
                modifier = Modifier.size(170.dp)
            )
        }

        // Women Section
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = 4.dp)
        ) {
            Icon(
                imageVector = Icons.Outlined.Woman,
                contentDescription = "Women Icon",
                tint = mainBlue,
                modifier = Modifier
                    .size(30.dp)
                    .padding(end = 4.dp)
            )
            Text(
                text = "Women",
                fontSize = 18.sp,
                color = mainBlue
            )
        }
        DrawerItem("Tops", iconResId = R.drawable.tops, color = mainPink, selected = true)
        DrawerItem("Skirts", iconResId = R.drawable.skirts, color = mainPink)
        DrawerItem("Pants", iconResId = R.drawable.pants, color = mainPink)
        DrawerItem("Dresses", iconResId = R.drawable.dresses, color = mainPink)
        DrawerItem("Jackets", iconResId = R.drawable.jackets, color = mainPink)

        Spacer(modifier = Modifier.height(24.dp))

        // Men Section
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = 4.dp)
        ) {
            Icon(
                imageVector = Icons.Outlined.Man,
                contentDescription = "Man Icon",
                tint = mainBlue,
                modifier = Modifier
                    .size(30.dp)
                    .padding(end = 4.dp)
            )
            Text(
                text = "Men",
                fontSize = 18.sp,
                color = mainBlue
            )
        }
        DrawerItem("T-shirts", iconResId = R.drawable.tops, color = mainBlue)
        DrawerItem("Pants", iconResId = R.drawable.pants, color = mainBlue)
        DrawerItem("Jackets", iconResId = R.drawable.jackets, color = mainBlue)

        Spacer(modifier = Modifier.weight(1f))

        // Logout
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .clickable { onLogoutClick() }
        ) {
            Icon(
                imageVector = Icons.Filled.Logout,
                contentDescription = "Logout",
                tint = Color.Red,
                modifier = Modifier.padding(end = 8.dp)
            )
            Button(
                onClick = onLogoutClick,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent,
                    contentColor = Color.Red
                ),
                elevation = ButtonDefaults.buttonElevation(0.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Log Out", fontSize = 16.sp)
            }
        }
    }
}

@Composable
fun DrawerItem(label: String, iconResId: Int, color: Color, selected: Boolean = false) {
    val bgColor = if (selected) lightTeal else Color.Transparent
    val borderColor = if (selected) popColor else Color.Transparent

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(bgColor, RoundedCornerShape(16.dp))
            .border(1.dp, borderColor, RoundedCornerShape(16.dp))
            .padding(horizontal = 12.dp, vertical = 4.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                painter = painterResource(id = iconResId),
                contentDescription = "$label Icon",
                tint = color,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = label, fontSize = 14.sp, color = color)
        }
    }
}