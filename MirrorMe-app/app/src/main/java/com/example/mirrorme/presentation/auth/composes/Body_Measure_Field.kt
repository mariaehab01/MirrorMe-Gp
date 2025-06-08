import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.input.ImeAction
import com.example.mirrorme.ui.theme.mainBlue

@Composable
fun BodyMeasureField(
    title: String,
    unit: String,
    initialValue: String = "",
    onValueChange: (String) -> Unit
) {
    var value by remember { mutableStateOf(initialValue) }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(vertical = 8.dp)
            .fillMaxWidth()
    ) {
        Text(
            text = title,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = mainBlue,
            modifier = Modifier.width(90.dp)
        )

        Spacer(modifier = Modifier.width(80.dp))

        TextField(
            value = value,
            onValueChange = {
                if (it.all { char -> char.isDigit() }) {
                    value = it
                    onValueChange(it)
                }
            },
            singleLine = true,
            modifier = Modifier
                .width(75.dp)
                .height(32.dp)
                .border(1.dp, Color.Black, RoundedCornerShape(4.dp)),
            textStyle = LocalTextStyle.current.copy(
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            ),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White
            )

        )

        Spacer(modifier = Modifier.width(8.dp))

        Text(
            text = unit,
            fontSize = 18.sp,
            color = Color(0xFF0A2A38)
        )
    }
}
