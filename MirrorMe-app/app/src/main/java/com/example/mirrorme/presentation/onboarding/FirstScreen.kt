package com.example.mirrorme.presentation.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.mirrorme.R
import com.example.mirrorme.presentation.onboarding.composes.RoundedButton
import com.example.mirrorme.presentation.onboarding.composes.SocialIcon
import com.example.mirrorme.ui.theme.mainBlue
import com.example.mirrorme.ui.theme.mainPink
import com.example.mirrorme.ui.theme.off_white
import com.example.mirrorme.ui.theme.lightBlue

@Composable
fun FirstScreen(navController: NavHostController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.shopping_bg),
            contentDescription = "Background",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(320.dp)
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(top = 270.dp)
                .clip(RoundedCornerShape(topStart = 60.dp, topEnd = 60.dp))
                .background(off_white)
                .padding(horizontal = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.page_indicators),
                contentDescription = "Dots Indicator",
                modifier = Modifier
                    .size(55.dp)
                    .padding(bottom = 30.dp)
                    .align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Image(
                painter = painterResource(id = R.drawable.mirrorme_bg),
                contentDescription = "Logo",
                modifier = Modifier
                    .height(120.dp)
                    .width(240.dp)
            )

            Text(
                text = "Welcome Back",
                fontSize = 28.sp,
                fontWeight = FontWeight.Medium,
                color = lightBlue,
                modifier = Modifier.padding(top = 2.dp, bottom = 20.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))
            RoundedButton(
                text = "Sign In",
                backgroundColor = mainPink,
                textColor = Color.White,
                onClick = { navController.navigate("signIn") }
            )

            Spacer(modifier = Modifier.height(25.dp))

            RoundedButton(
                text = "Sign Up",
                backgroundColor = mainBlue,
                textColor = Color.White,
                onClick = { navController.navigate("signUp") }
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                "~Or~",
                color = lightBlue,
                fontWeight = FontWeight.Medium,
                fontSize = 26.sp,
                modifier = Modifier.padding(vertical = 8.dp),
                style = TextStyle(textAlign = TextAlign.Center)
            )

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(110.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                SocialIcon(R.drawable.devicon_google)
                SocialIcon(R.drawable.logos_facebook)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FirstScreenPreview() {
    FirstScreen(navController = rememberNavController())
}
