package com.example.mirrorme.presentation.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.example.mirrorme.R
import com.example.mirrorme.presentation.auth.SignUp
import com.example.mirrorme.ui.theme.MirrorMeTheme
import com.example.mirrorme.ui.theme.background
import com.example.mirrorme.ui.theme.mainBlue
import com.example.mirrorme.ui.theme.popColor
import kotlinx.coroutines.delay

@SuppressLint("CustomSplashScreen")
class SplashActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        super.onCreate(savedInstanceState)

        setContent {
            MirrorMeTheme {
                SplashScreenUI {
                    startActivity(Intent(this, SignUp::class.java))
                    finish()
                }
            }
        }
    }
}

@Composable
fun SplashScreenUI(onTimeout: () -> Unit = {}) {
    var isContentVisible by remember { mutableStateOf(false) }
    var shouldBlink by remember { mutableStateOf(false) }
    var screenWidthPx = 0
    var startSlideOut by remember { mutableStateOf(false) }

    val slideOffsetX by animateDpAsState(
        targetValue = if (startSlideOut) (-1000).dp else 0.dp, // Pull to the left
        animationSpec = tween(durationMillis = 1600, easing = LinearOutSlowInEasing),
        finishedListener = {
            if (startSlideOut) {
                onTimeout() // Trigger home screen transition
            }
        }
    )
    val textColor by animateColorAsState(
        targetValue = if (shouldBlink) popColor else mainBlue,
        animationSpec = infiniteRepeatable(
            animation = keyframes {
                durationMillis = 2400
                popColor at 0       // Hold popColor
                popColor at 1200
                mainBlue at 2400    // Transition to blue
            },
            repeatMode = RepeatMode.Restart
        )
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .onSizeChanged { size ->
                screenWidthPx = size.width
            }.offset(x = slideOffsetX)
    ) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = background
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                AnimatedVisibility(
                    visible = isContentVisible,
                    enter = slideInHorizontally(
                        animationSpec = tween(durationMillis = 1000, easing = LinearOutSlowInEasing),
                        initialOffsetX = { -it }
                    ) + fadeIn(animationSpec = tween(1000)),
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
                        animationSpec = tween(durationMillis = 1000, easing = LinearOutSlowInEasing),
                        initialOffsetX = { it }
                    ) + fadeIn(animationSpec = tween(100)), // make it start from the right most side
                    exit = fadeOut()
                ) {
                    Box(modifier = Modifier.offset(x = 35.dp)) { //control the place of the beginning of the slogan
                        if (textColor == popColor) {
                            val glowColor = popColor.copy(alpha = 0.25f)// shadow spreading
                            val offsets = listOf(
                                Offset(-1f, -1f), Offset(1f, -1f),
                                Offset(-1f, 1f), Offset(1f, 1f),
                                Offset(0f, -1f), Offset(0f, 1f),
                                Offset(-1f, 0f), Offset(1f, 0f)
                            )

                            offsets.forEach { offset ->
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
                                    modifier = Modifier.offset(
                                        x = offset.x.dp,
                                        y = offset.y.dp
                                    )
                                )
                            }
                        }
                        // Actual text remains in its normal state
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
                    delay(1400) // Delay before starting the blink effect
                    shouldBlink = true
                    delay(4200)
                    startSlideOut=true
                }
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
fun SplashScreenPreview() {
    MirrorMeTheme {
        SplashScreenUI()
    }
}