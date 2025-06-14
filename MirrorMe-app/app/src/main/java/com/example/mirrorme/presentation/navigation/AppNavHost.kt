package com.example.mirrorme.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.mirrorme.presentation.auth.BodyInfoContent
import com.example.mirrorme.presentation.home.HomeScreen
import com.example.mirrorme.presentation.auth.SignInContent
import com.example.mirrorme.presentation.auth.SignUpContent
import com.example.mirrorme.presentation.onboarding.FirstScreen

@Composable
fun AppNavHost(navController: NavHostController = rememberNavController()) {
    NavHost(navController = navController, startDestination = "firstScreen") {
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
            BodyInfoContent(gender, phone)
        }
        composable("home") { HomeScreen(navController) }
    }
}
