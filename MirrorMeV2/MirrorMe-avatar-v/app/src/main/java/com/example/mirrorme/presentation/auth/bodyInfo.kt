package com.example.mirrorme.presentation.auth

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.mirrorme.di.ServiceLocator
import com.example.mirrorme.presentation.auth.composes.BodyInfoScroller
import com.example.mirrorme.presentation.auth.composes.BodyMeasureField
import com.example.mirrorme.presentation.auth.composes.PageHeader
import com.example.mirrorme.presentation.auth.composes.fieldsContainer
import com.example.mirrorme.presentation.auth.composes.BodyInfoItem
import com.example.mirrorme.ui.theme.gradient
import com.example.mirrorme.ui.theme.mainPink

@Composable
fun BodyInfoContent(
    gender: String,
    phone: String,
    viewModel: AuthViewModel = viewModel(),
    onSaveProfileSuccess: () -> Unit = {},
    navController: NavHostController,

    ) {
    val skinToneData = getSkinTones()
    val bodyData = if (gender.lowercase() == "male") getMaleBodies() else getFemaleBodies()

    var selectedBodyIndex by remember { mutableIntStateOf(0) }
    var selectedBodyShape by remember { mutableStateOf((bodyData.firstOrNull() as? BodyInfoItem.ImageItem)?.title ?: "Hourglass") }

    var selectedToneIndex by remember { mutableIntStateOf(0) }
    var selectedSkinTone by remember {
        mutableStateOf((skinToneData.firstOrNull() as? BodyInfoItem.ColorItem)?.title ?: "Unknown")
    }

    var height by remember { mutableStateOf("") }
    var weight by remember { mutableStateOf("") }
    var saveProfileError by remember { mutableStateOf<String?>(null) }
    var showWelcomeMessage by remember { mutableStateOf(false) }

    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(uiState) {
        when (uiState) {
            is AuthUiState.Success -> {
                ServiceLocator.setLastScreenUseCase("home")
                Toast.makeText(context, "Sign-up successful", Toast.LENGTH_SHORT).show()
                navController.navigate("home") {
                    popUpTo("bodyInfo") { inclusive = true }
                }
                onSaveProfileSuccess()
            }
            is AuthUiState.Error -> saveProfileError = (uiState as AuthUiState.Error).message
            else -> {}
        }
    }

    saveProfileError?.let { errorMsg ->
        Toast.makeText(context, errorMsg, Toast.LENGTH_SHORT).show()
        navController.navigate("signup") {
            popUpTo("bodyInfo") { inclusive = true }
        }
    }


    Box {
        PageHeader(
            "Wooh,", "Almost There...",
            modifier = Modifier
                .fillMaxSize()
                .background(gradient)
                .padding(top = 60.dp, start = 36.dp)
        )

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            val scrollState = rememberScrollState()

            fieldsContainer("Body Info", heightFraction = 0.7f,
                modifier = Modifier.padding(start = 40.dp, end = 40.dp, top = 180.dp)) {

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .verticalScroll(scrollState)
                ) {
                    Spacer(modifier = Modifier.height(5.dp))
                    BodyMeasureField("Height", "cm", height) { height = it }
                    Spacer(modifier = Modifier.height(5.dp))
                    BodyMeasureField("Weight", "Kg", weight) { weight = it }
                    Spacer(modifier = Modifier.height(10.dp))

                    BodyInfoScroller(
                        title = "Body Shape",
                        items = bodyData,
                        onItemSelected = { index, _ ->
                            selectedBodyIndex = index
                            selectedBodyShape = (bodyData[index] as? BodyInfoItem.ImageItem)?.title ?: "Unknown"
                        }
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    BodyInfoScroller(
                        title = "Skin Tone",
                        items = skinToneData,
                        shape = CircleShape,
                        onItemSelected = { index, _ ->
                            selectedToneIndex = index
                            selectedSkinTone = (skinToneData[index] as? BodyInfoItem.ColorItem)?.title ?: "Unknown"
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(23.dp))

            Button(
                onClick = {
                    if (height.isNotBlank() && weight.isNotBlank()) {
                        val heightInt = height.toIntOrNull()
                        val weightInt = weight.toIntOrNull()

                        if (heightInt != null && weightInt != null) {
                            if (selectedBodyIndex < bodyData.size && selectedToneIndex < skinToneData.size) {
                                viewModel.saveProfile(
                                    phone = phone,
                                    height = heightInt,
                                    weight = weightInt,
                                    bodyShape = selectedBodyShape,
                                    skinTone = selectedSkinTone,
                                    gender = gender
                                )
                            } else {
                                saveProfileError = "Invalid body shape or skin tone selection"
                            }
                        } else {
                            saveProfileError = "Height and weight must be valid numbers"
                        }
                    } else {
                        saveProfileError = "Please enter height and weight"
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = mainPink),
                shape = RoundedCornerShape(50),
                modifier = Modifier
                    .width(200.dp)
                    .height(50.dp)
            ) {
                if (uiState == AuthUiState.Loading) {
                    CircularProgressIndicator(
                        color = Color.White,
                        strokeWidth = 2.dp,
                        modifier = Modifier.size(24.dp)
                    )
                } else {
                    Text("Save", fontWeight = FontWeight.Bold, color = Color.White)
                }
            }

            saveProfileError?.let {
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = it, color = Color.Red, fontWeight = FontWeight.Bold)
            }

            if (showWelcomeMessage) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Welcome!",
                    color = mainPink,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
            }

            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}
