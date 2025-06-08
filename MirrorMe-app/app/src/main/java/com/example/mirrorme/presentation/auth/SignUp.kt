package com.example.mirrorme.presentation.auth

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.material.icons.rounded.MailOutline
import androidx.compose.material.icons.rounded.Phone
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mirrorme.presentation.auth.composes.PageHeader
import com.example.mirrorme.presentation.auth.composes.PasswordField
import com.example.mirrorme.presentation.auth.composes.RoundedTextField
import com.example.mirrorme.presentation.auth.composes.fieldsContainer
import com.example.mirrorme.ui.theme.MirrorMeTheme
import com.example.mirrorme.ui.theme.gradient
import com.example.mirrorme.ui.theme.mainBlue
import com.example.mirrorme.ui.theme.mainPink


class SignUp : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MirrorMeTheme {
                SignUpContent()
            }
        }
    }
}

@Composable
fun SignUpContent() {
    var email by remember { mutableStateOf("") }
    Box{
        PageHeader(
            line1 = "Let’s", line2 = "Create Account",
            modifier = Modifier
                .fillMaxSize()
                .background(gradient)
                .padding(top = 70.dp, start = 36.dp),
            contentAlignment = Alignment.TopStart
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            fieldsContainer(
                "Sign Up",
                0.7f,
                modifier = Modifier.padding(start = 36.dp, end = 36.dp, top = 190.dp)
            ) {
                RoundedTextField(
                    value = email,
                    placeholder = "Enter your email",
                    icon = Icons.Rounded.MailOutline,
                    onValueChange = {})
                Spacer(Modifier.height(12.dp))
                RoundedTextField(
                    placeholder = "Enter your phone",
                    icon = Icons.Rounded.Phone,
                    value = "",
                    onValueChange = {})
                Spacer(Modifier.height(12.dp))
                PasswordField(
                    placeholder = "Enter your password",
                    icon = Icons.Rounded.Lock,
                    value = "",
                    onValueChange = {})
                Spacer(Modifier.height(12.dp))
                PasswordField(
                    placeholder = "Confirm password",
                    icon = Icons.Rounded.Lock,
                    value = "",
                    onValueChange = {})
                Spacer(Modifier.height(20.dp))

                Text(
                    text = "Gender",
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                    color = Color(0xFF0D2137),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp)
                        .wrapContentWidth(Alignment.Start) // aligns text to the start of the Column
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 12.dp, top = 10.dp)
                        .wrapContentWidth(Alignment.Start),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    Checkbox(checked = false, onCheckedChange = {})
                    Text("Male",
                        modifier = Modifier.padding(end = 16.dp),
                        color = mainBlue,
                        fontSize = 20.sp,)
                    Spacer(Modifier.width(20.dp))
                    Checkbox(checked = true, onCheckedChange = {})
                    Text("Female",
                        color = mainBlue,
                        fontSize = 20.sp,)
                }
            }
            Spacer(modifier = Modifier.height(30.dp))

            Button(
                onClick = { /* TODO */ },
                colors = ButtonDefaults.buttonColors(containerColor = mainPink),
                shape = RoundedCornerShape(50),
                modifier = Modifier
                    .width(200.dp)
                    .height(50.dp)

                ) {
                Text("Next", fontWeight = FontWeight.Bold, color = Color.White)
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


@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
fun SignUpContentPreview() {
    MirrorMeTheme {
        SignUpContent()
    }
}