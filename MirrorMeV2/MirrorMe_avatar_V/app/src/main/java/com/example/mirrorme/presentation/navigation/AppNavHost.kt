package com.example.mirrorme.presentation.navigation

import androidx.compose.runtime.Composable
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
import com.example.mirrorme.presentation.avatarTryOn.WebViewScreen
import com.example.mirrorme.presentation.itemDetails.ItemInfoScreen
import com.example.mirrorme.presentation.onboarding.FirstScreen
import com.example.mirrorme.presentation.splash.SplashScreen

@Composable
fun AppNavHost(navController: NavHostController = rememberNavController()) {
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
            ItemInfoScreen(productId = productId, navController = navController)
        }
        composable(
            route = "webView/{height}/{weight}/{gender}/{bodyShape}/{skinTone}",
            arguments = listOf(
                navArgument("height") { type = NavType.IntType },
                navArgument("weight") { type = NavType.IntType },
                navArgument("gender") { type = NavType.StringType },
                navArgument("bodyShape") { type = NavType.StringType },
                navArgument("skinTone") { type = NavType.StringType },
            )
        ) { backStackEntry ->
            val height = backStackEntry.arguments?.getInt("height") ?: 0
            val weight = backStackEntry.arguments?.getInt("weight") ?: 0
            val gender = backStackEntry.arguments?.getString("gender") ?: ""
            val bodyShape = backStackEntry.arguments?.getString("bodyShape") ?: ""
            val skinTone = backStackEntry.arguments?.getString("skinTone") ?: ""

            WebViewScreen(height, weight, gender, bodyShape, skinTone)
        }


    }
}
