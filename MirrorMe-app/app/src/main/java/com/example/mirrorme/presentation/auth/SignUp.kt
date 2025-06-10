package com.example.mirrorme.presentation.auth

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.material.icons.rounded.MailOutline
import androidx.compose.material.icons.rounded.Phone
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import androidx.navigation.navArgument
import com.example.mirrorme.presentation.auth.composes.*
import com.example.mirrorme.ui.theme.MirrorMeTheme
import com.example.mirrorme.ui.theme.gradient
import com.example.mirrorme.ui.theme.mainBlue
import com.example.mirrorme.ui.theme.mainPink

class SignUp : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MirrorMeTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "signUp") {
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
                }
            }
        }
    }
}

@Composable
fun SignUpContent(
    navController: NavHostController,
    viewModel: AuthViewModel = viewModel()
) {
    var email by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var emailError by remember { mutableStateOf<String?>(null) }
    var phoneError by remember { mutableStateOf<String?>(null) }
    var passwordError by remember { mutableStateOf<String?>(null) }
    var confirmPasswordError by remember { mutableStateOf<String?>(null) }
    var selectedGender by remember { mutableStateOf("Female") }

    var showSuccessDialog by remember { mutableStateOf(false) }

    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(uiState) {
        when (uiState) {
            is AuthUiState.Success -> {
                navController.navigate("bodyInfo/${selectedGender.lowercase()}?phone=$phone") {
                    popUpTo("signUp") { inclusive = true }
                }
            }
            is AuthUiState.Error -> {
                showSuccessDialog = true
            }
            else -> Unit
        }
    }

    if (showSuccessDialog) {
        AlertDialog(
            onDismissRequest = { showSuccessDialog = false },
            confirmButton = {
                TextButton(onClick = {
                    showSuccessDialog = false
                }) {
                    Text("Ok", color = mainPink, fontWeight = FontWeight.Bold)
                }
            },
            title = { Text("Verify Your Email", fontWeight = FontWeight.Bold) },
            text = { Text("We've sent a confirmation link to your email. Please verify it before continuing.") },
            containerColor = Color.White
        )
    }

    // Main content of the Sign Up screen
    Box {
        PageHeader(
            line1 = "Let’s", line2 = "Create Account",
            modifier = Modifier
                .fillMaxSize()
                .background(gradient)
                .padding(top = 60.dp, start = 36.dp),
        )

        Column(
            modifier = Modifier
                .padding(start = 40.dp, end = 40.dp, top = 180.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            fieldsContainer("Sign Up", 0.7f, modifier = Modifier.fillMaxWidth()) {
                val scrollState = rememberScrollState()
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .verticalScroll(scrollState)
                ) {
                    RoundedTextField(
                        value = email,
                        placeholder = "Enter your email",
                        icon = Icons.Rounded.MailOutline,
                        onValueChange = { email = it },
                        errorMessage = emailError
                    )

                    Spacer(Modifier.height(12.dp))

                    RoundedTextField(
                        value = phone,
                        placeholder = "Enter your phone",
                        icon = Icons.Rounded.Phone,
                        onValueChange = { phone = it },
                        errorMessage = phoneError
                    )

                    Spacer(Modifier.height(12.dp))

                    PasswordField(
                        value = password,
                        placeholder = "Enter your password",
                        icon = Icons.Rounded.Lock,
                        onValueChange = { password = it },
                        errorMessage = passwordError
                    )

                    Spacer(Modifier.height(12.dp))

                    PasswordField(
                        value = confirmPassword,
                        placeholder = "Confirm password",
                        icon = Icons.Rounded.Lock,
                        onValueChange = { confirmPassword = it },
                        confirmPassword = password,
                        confirmErrorMessage = confirmPasswordError
                    )

                    Spacer(Modifier.height(18.dp))

                    Text(
                        text = "Gender",
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp,
                        color = Color(0xFF0D2137),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 16.dp)
                            .wrapContentWidth(Alignment.Start)
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 12.dp, top = 2.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        Checkbox(
                            checked = selectedGender == "Male",
                            onCheckedChange = { isChecked ->
                                if (isChecked) selectedGender = "Male"
                            },
                            colors = CheckboxDefaults.colors(
                                checkedColor = mainBlue,
                                checkmarkColor = Color.White
                            )
                        )
                        Text("Male", modifier = Modifier.padding(end = 16.dp), color = mainBlue, fontSize = 18.sp)

                        Spacer(Modifier.width(20.dp))

                        Checkbox(
                            checked = selectedGender == "Female",
                            onCheckedChange = { isChecked ->
                                if (isChecked) selectedGender = "Female"
                            },
                            colors = CheckboxDefaults.colors(
                                checkedColor = mainBlue,
                                checkmarkColor = Color.White
                            )
                        )
                        Text("Female", color = mainBlue, fontSize = 18.sp)
                    }
                }
            }
            Spacer(modifier = Modifier.height(23.dp))

            Button(
                onClick = {
                    emailError = if (android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) null else "Invalid email"
                    phoneError = if (phone.all { it.isDigit() } && phone.length >= 7) null else "Invalid phone"
                    passwordError = if (Regex("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#\$%^&+=!]).{8,}").matches(password)) null else "Password too weak"
                    confirmPasswordError = if (confirmPassword == password) null else "Passwords do not match"

                    if (emailError == null && phoneError == null && passwordError == null && confirmPasswordError == null) {
                        viewModel.signUp(email, password)

                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = mainPink),
                shape = RoundedCornerShape(50),
                modifier = Modifier.width(200.dp).height(50.dp)
            ) {
                if (uiState == AuthUiState.Loading) {
                    CircularProgressIndicator(color = Color.White, strokeWidth = 2.dp, modifier = Modifier.size(24.dp))
                } else {
                    Text("Next", fontWeight = FontWeight.Bold, color = Color.White)
                }
            }

            Spacer(modifier = Modifier.height(20.dp))
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 40.dp, start = 36.dp, end = 36.dp),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.End
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text("Have an account?", color = Color.White)
                Text(
                    text = "Sign In",
                    color = mainPink,
                    fontWeight = FontWeight.Bold,
                    textDecoration = TextDecoration.Underline
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SignUpContentPreview() {
    MirrorMeTheme {
        SignUpContent(navController = rememberNavController())
    }
}
