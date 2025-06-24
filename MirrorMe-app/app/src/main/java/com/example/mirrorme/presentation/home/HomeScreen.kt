package com.example.mirrorme.presentation.home

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.mirrorme.R
import com.example.mirrorme.presentation.home.composes.*
import com.example.mirrorme.ui.theme.lightBlue
import com.example.mirrorme.ui.theme.mainPink
import com.example.mirrorme.domain.model.Product
import com.example.mirrorme.presentation.onboarding.FirstScreen
import com.example.mirrorme.ui.theme.off_white
import kotlinx.coroutines.launch
import androidx.compose.runtime.getValue
import com.example.mirrorme.di.ServiceLocator

@Composable
fun HomeScreen(navController: NavHostController) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val viewModel: HomeViewModel = viewModel() // ✅ NEW
    val products by viewModel.products.collectAsState() // ✅ NEW

    LaunchedEffect(Unit) {
        viewModel.loadProducts() // ✅ NEW
    }

    LaunchedEffect(products) {
        Log.d("HomeScreen", "Products received: ${products.size}")
    }

    ModalNavigationDrawer(
        drawerState = drawerState,               // ← controls open / close
        drawerContent = {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(240.dp)
                    .clip(RoundedCornerShape(topEnd = 32.dp, bottomEnd = 32.dp))
                    .background(off_white)
            ) {
                AppDrawerContent(
                    onLogoutClick = {
                        ServiceLocator.setLastScreenUseCase("signIn")
                        navController.navigate("SignIn") {
                            popUpTo("home") { inclusive = true }
                        }
                    }
                )
            }
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(off_white)
        ) {
            TopBarSection(onMenuClick = {
                scope.launch { drawerState.open() }
            })
            TitleSection(category = "Home")
            BannerSection(bannerRes = R.drawable.home_banner)
            SearchBarSection()

            Spacer(modifier = Modifier.height(16.dp))

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .weight(1f)
            ) {
                items(products) { item -> // ✅ CHANGED from items = static list
                    Log.d("HomeScreen", "Rendering item: ${item.name} - ${item.imageUrl}")
                    ProductItem(item = item) {
                        // TODO: navigate to product details
                    }
                }
            }
            BottomNavBar(selectedPage = "home")
        }

    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen(navController = rememberNavController())
}
