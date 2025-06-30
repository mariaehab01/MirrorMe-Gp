package com.example.mirrorme.presentation.splash

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.*
import androidx.navigation.NavHostController
import com.example.mirrorme.R
import com.example.mirrorme.di.ServiceLocator
import com.example.mirrorme.ui.theme.*

import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavHostController) {
    SplashScreenUI {
        val lastScreen = ServiceLocator.getLastScreenUseCase()
        val targetScreen = when (lastScreen) {
            "firstScreen" -> "firstScreen"
            "signUp" -> "signUp"
            "signIn" -> "signIn"
            "home" -> "home"
            else -> "firstScreen"
        }

        navController.navigate(targetScreen) {
            popUpTo("splash") { inclusive = true }
        }
    }
}

@Composable
fun SplashScreenUI(onTimeout: () -> Unit = {}) {
    var isContentVisible by remember { mutableStateOf(false) }
    var shouldBlink by remember { mutableStateOf(false) }
    var startSlideOut by remember { mutableStateOf(false) }

    val slideOffsetX by animateDpAsState(
        targetValue = if (startSlideOut) (-1000).dp else 0.dp,
        animationSpec = tween(durationMillis = 1600, easing = LinearOutSlowInEasing)
    )

    val textColor by animateColorAsState(
        targetValue = if (shouldBlink) popColor else mainBlue,
        animationSpec = infiniteRepeatable(
            animation = keyframes {
                durationMillis = 2400
                popColor at 0
                popColor at 1200
                mainBlue at 2400
            },
            repeatMode = RepeatMode.Restart
        )
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .offset(x = slideOffsetX)
    ) {
        Surface(modifier = Modifier.fillMaxSize(), color = background) {
            Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                AnimatedVisibility(
                    visible = isContentVisible,
                    enter = slideInHorizontally(
                        animationSpec = tween(1000, easing = LinearOutSlowInEasing),
                        initialOffsetX = { -it }
                    ) + fadeIn(),
                    exit = fadeOut()
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.splash_img),
                        contentDescription = "Splash Image",
                        modifier = Modifier
                            .size(300.dp)
                            .offset(y = (-50).dp),
                        contentScale = ContentScale.Fit
                    )
                }

                AnimatedVisibility(
                    visible = isContentVisible,
                    enter = slideInHorizontally(
                        animationSpec = tween(1000, easing = LinearOutSlowInEasing),
                        initialOffsetX = { it }
                    ) + fadeIn(),
                    exit = fadeOut()
                ) {
                    Box(modifier = Modifier.offset(x = 35.dp)) {
                        if (textColor == popColor) {
                            val glowColor = popColor.copy(alpha = 0.25f)
                            val offsets = listOf(
                                Offset(-1f, -1f), Offset(1f, -1f),
                                Offset(-1f, 1f), Offset(1f, 1f),
                                Offset(0f, -1f), Offset(0f, 1f),
                                Offset(-1f, 0f), Offset(1f, 0f)
                            )
                            offsets.forEach {
                                Text(
                                    text = "Virtual Try On",
                                    style = TextStyle(
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.Normal,
                                        color = glowColor,
                                        letterSpacing = 0.07.em,
                                        lineHeight = 29.sp,
                                        textAlign = TextAlign.Center
                                    ),
                                    modifier = Modifier.offset(x = it.x.dp, y = it.y.dp)
                                )
                            }
                        }
                        Text(
                            text = "Virtual Try On",
                            style = TextStyle(
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Normal,
                                color = textColor,
                                letterSpacing = 0.07.em,
                                lineHeight = 29.sp,
                                textAlign = TextAlign.Center
                            )
                        )
                    }
                }

                LaunchedEffect(Unit) {
                    delay(500)
                    isContentVisible = true
                    delay(1400)
                    shouldBlink = true
                    delay(3800)
                    startSlideOut = true
                    onTimeout()
                }
            }
        }
    }
}
