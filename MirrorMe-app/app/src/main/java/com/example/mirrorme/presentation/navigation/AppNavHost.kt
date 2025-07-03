package com.example.mirrorme.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.mirrorme.di.ServiceLocator
import com.example.mirrorme.presentation.auth.BodyInfoContent
import com.example.mirrorme.presentation.home.HomeScreen
import com.example.mirrorme.presentation.auth.SignInContent
import com.example.mirrorme.presentation.auth.SignUpContent
import com.example.mirrorme.presentation.cart.CartScreen
import com.example.mirrorme.presentation.cart.CartViewModel
import com.example.mirrorme.presentation.itemDetails.ItemInfoScreen
import com.example.mirrorme.presentation.onboarding.FirstScreen
import com.example.mirrorme.presentation.ratingsAndReviews.RatingsAndReviewsScreen
import com.example.mirrorme.presentation.splash.SplashScreen

@Composable
fun AppNavHost(navController: NavHostController = rememberNavController()) {
    val cartViewModel: CartViewModel = viewModel()

    NavHost(navController = navController, startDestination = "splash") {
        composable("splash") {
            SplashScreen(navController = navController)
//            val lastScreen = ServiceLocator.getLastScreenUseCase()
//            val targetScreen = when (lastScreen) {
//                "firstScreen" -> "firstScreen"
//                "signUp" -> "signUp"
//                "home" -> "home"
//                else -> "firstScreen"
//            }
//            navController.navigate(targetScreen) {
//                popUpTo("splash") { inclusive = true }
//            }
//            SplashScreen(navController)
        }
        composable("firstScreen") { FirstScreen(navController) }
        composable("signIn") { SignInContent(navController) }
        composable("signUp") {
            SignUpContent(navController = navController)
        }
        composable(
            route = "bodyInfo/{gender}?email={email}&password={password}&phone={phone}",
            arguments = listOf(
                navArgument("gender") { defaultValue = "unknown" },
                navArgument("phone") { defaultValue = "" }
            )
        ) { backStackEntry ->
            val gender = backStackEntry.arguments?.getString("gender") ?: "unknown"
            val phone = backStackEntry.arguments?.getString("phone") ?: ""
            BodyInfoContent(gender, phone,
                navController = navController,
                onSaveProfileSuccess = {
                    ServiceLocator.setLastScreenUseCase("home")
                }
            )
        }
        composable("home") { HomeScreen(navController) }
        composable(
            route = "itemInfo/{productId}",
            arguments = listOf(navArgument("productId") { type = NavType.IntType })
        ) { backStackEntry ->
            val productId = backStackEntry.arguments?.getInt("productId") ?: 1
            ItemInfoScreen(productId = productId, navController = navController, cartViewModel)
        }
        composable("ratingsAndReviews") {
            RatingsAndReviewsScreen(navController)
        }

        composable("cart") {
            CartScreen(navController, cartViewModel = cartViewModel)
        }

    }
}
