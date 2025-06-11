package com.example.mirrorme.presentation.auth

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.material.icons.rounded.MailOutline
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mirrorme.presentation.auth.composes.*
import com.example.mirrorme.ui.theme.MirrorMeTheme
import com.example.mirrorme.ui.theme.gradient
import com.example.mirrorme.ui.theme.lightBlue
import com.example.mirrorme.ui.theme.mainBlue
import com.example.mirrorme.ui.theme.mainPink
import com.example.mirrorme.R

class SignIn : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MirrorMeTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "signIn") {
                    composable("signIn") {
                        SignInContent(navController = navController)
                    }
                    composable("signUp") {
                        SignUpContent(navController = navController)
                    }
                }
            }
        }
    }
}

@Composable
fun SignInContent(
    navController: NavHostController,
    viewModel: AuthViewModel = viewModel()
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var emailError by remember { mutableStateOf<String?>(null) }
    var passwordError by remember { mutableStateOf<String?>(null) }

    val uiState by viewModel.uiState.collectAsState()

    // Handle success navigation
    val context = LocalContext.current

    LaunchedEffect(uiState) {
        when (uiState) {
            is AuthUiState.Success -> {
                Toast.makeText(context, "Sign-in successful", Toast.LENGTH_SHORT).show()
                //navController.navigate("home")
            }
            is AuthUiState.Error -> {
                Toast.makeText(context, (uiState as AuthUiState.Error).message, Toast.LENGTH_LONG).show()
            }
            else -> Unit
        }
    }


    Box {
        PageHeader(
            line1 = "Hey,", line2 = "Welcome Back!",
            modifier = Modifier
                .fillMaxSize()
                .background(gradient)
                .padding(top = 60.dp, start = 36.dp),
        )

        Column(
            modifier = Modifier
                .padding(start = 40.dp, end = 40.dp, top = 200.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            fieldsContainer("Sign In", 0.48f, modifier = Modifier.fillMaxWidth()) {
                val scrollState = rememberScrollState()
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .verticalScroll(scrollState)
                ) {
                    RoundedTextField(
                        value = email,
                        placeholder = "Enter your email",
                        onValueChange = { email = it },
                        errorMessage = emailError
                    )

                    Spacer(Modifier.height(20.dp))

                    PasswordField(
                        value = password,
                        placeholder = "Enter your password",
                        onValueChange = { password = it },
                        errorMessage = passwordError
                    )

                    Spacer(Modifier.height(20.dp))

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = 4.dp),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        Text(
                            text = "Forgot Password?",
                            color = lightBlue,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            textDecoration = TextDecoration.Underline,
//                            modifier = Modifier.clickable {
//                                // TODO: Navigate to Forgot Password screen
//                                // navController.navigate("forgotPassword")
//                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(50.dp))

            // Sign In button
            Button(
                onClick = {
                    emailError = if (android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) null else "Invalid email"
                    passwordError = if (password.length >= 6) null else "Password too short"

                    if (emailError == null && passwordError == null) {
                        // TODO: Navigate to next screen after successful sign in
                        viewModel.signIn(email, password)
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = mainPink),
                shape = RoundedCornerShape(50),
                modifier = Modifier
                    .width(200.dp)
                    .height(50.dp),
            ) {
                Text("Login", fontWeight = FontWeight.Bold, color = Color.White)
            }

            Spacer(modifier = Modifier.height(20.dp))
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 60.dp, start = 36.dp, end = 36.dp),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.End
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text("Don't have an account?", color = Color.White)
                Text(
                    text = "Sign Up",
                    color = mainPink,
                    fontWeight = FontWeight.Bold,
                    textDecoration = TextDecoration.Underline,
                    modifier = Modifier.clickable {
                        navController.navigate("signUp")
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SignInContentPreview() {
    MirrorMeTheme {
        SignInContent(navController = rememberNavController())
    }
}
