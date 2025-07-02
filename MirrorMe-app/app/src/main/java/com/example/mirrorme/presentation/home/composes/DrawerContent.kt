package com.example.mirrorme.presentation.home.composes

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.outlined.Man
import androidx.compose.material.icons.outlined.Woman
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mirrorme.R
import com.example.mirrorme.ui.theme.*

@Composable
fun AppDrawerContent(
    selectedCategory: String?,
    selectedGender: String?,
    onCategorySelected: (String?, String?) -> Unit,
    onLogoutClick: () -> Unit
) {
    val femaleGroups = listOf(
        "Tops" to listOf("top", "blouse", "hoodie", "t-shirt", "polo", "longsleeve"),
        "Pants" to listOf("pants"),
        "Skirts" to listOf("skirt"),
        "Dresses" to listOf("dress"),
        "Jackets" to listOf("blazer", "outwear"),
        "Shoes" to listOf("shoes")

    )

    val maleGroups = listOf(
        "T-shirts" to listOf("hoodie", "t-shirt", "polo", "longsleeve"),
        "Jackets" to listOf("blazer", "outwear"),
        "Pants" to listOf("pants"),
        "Shoes" to listOf("shoes")
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(off_white)
            .padding(horizontal = 16.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.mirrorme_bg),
                contentDescription = "Logo",
                modifier = Modifier.size(170.dp)
            )
        }

        //Female Section
        SectionHeader(icon = Icons.Outlined.Woman, label = "Women")
        femaleGroups.forEach { (label, subcategories) ->
            DrawerItem(
                label = label,
                iconResId = getCategoryIcon(subcategories.first()),
                color = mainPink,
                isSelected = selectedCategory?.lowercase() == label.lowercase() && selectedGender == "female"
            ) {
                onCategorySelected(label.lowercase(), "female")
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        //Male Section
        SectionHeader(icon = Icons.Outlined.Man, label = "Men")
        maleGroups.forEach { (label, subcategories) ->
            DrawerItem(
                label = label,
                iconResId = getCategoryIcon(subcategories.first()),
                color = mainBlue,
                isSelected = selectedCategory?.lowercase() == label.lowercase() && selectedGender == "male"
            ) {
                onCategorySelected(label.lowercase(), "male")
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        //Logout Button
        LogoutItem(onLogoutClick = onLogoutClick)

        Spacer(modifier = Modifier.height(20.dp))
    }
}

@Composable
fun SectionHeader(icon: ImageVector, label: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(bottom = 4.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = "$label Icon",
            tint = mainBlue,
            modifier = Modifier.size(30.dp).padding(end = 4.dp)
        )
        Text(text = label, fontSize = 18.sp, color = mainBlue)
    }
}

@Composable
fun DrawerItem(
    label: String,
    iconResId: Int,
    color: Color,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val bgColor = if (isSelected) lightTeal else Color.Transparent
    val borderColor = if (isSelected) popColor else Color.Transparent

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
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

@Composable
fun LogoutItem(onLogoutClick: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .clickable { onLogoutClick() }
            .fillMaxWidth()
            .padding(vertical = 16.dp)
            .background(Color.Transparent)
            .padding(horizontal = 12.dp)
    ) {
        Icon(
            imageVector = Icons.Filled.Logout,
            contentDescription = "Logout",
            tint = Color.Red,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = "Log Out",
            fontSize = 20.sp,
            color = Color.Red,
            fontWeight = FontWeight.Bold
        )
    }
}


fun getCategoryIcon(category: String): Int {
    return when (category.lowercase()) {
        "tops", "top", "t-shirt", "polo", "longsleeve" -> R.drawable.tops
        "pants" -> R.drawable.pants
        "skirts", "skirt" -> R.drawable.skirts
        "dresses", "dress" -> R.drawable.dresses
        "jackets", "blazer", "outwear" -> R.drawable.jackets
        else -> R.drawable.shoes
    }
}
