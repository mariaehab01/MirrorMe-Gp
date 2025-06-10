import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.Alignment
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Text
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.mirrorme.R
import com.example.mirrorme.ui.theme.mainBlue

sealed class BodyInfoItem {
    data class ImageItem(
        val painter: Painter,
        val width: Dp = 80.dp,
        val height: Dp = 120.dp
    ) : BodyInfoItem()

    data class ColorItem(val color: Color) : BodyInfoItem()
}

@Composable
fun BodyInfoScroller(
    title: String,
    items: List<BodyInfoItem>,
    shape: RoundedCornerShape = RoundedCornerShape(12.dp),
    onItemSelected: (Int) -> Unit
) {
    var selectedIndex by remember { mutableStateOf(0) }
    val scrollState = rememberScrollState()

    Column {
        Text(
            title,
            fontSize = 22.sp,
            fontWeight = FontWeight.Medium,
            color = mainBlue,
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(scrollState)
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            items.forEachIndexed { index, item ->
                val isSelected = index == selectedIndex

                val borderModifier = Modifier.border(
                    width = if (isSelected) 3.dp else 0.dp,
                    color = if (isSelected) Color.Cyan else Color.Transparent,
                    shape = shape
                )

                when (item) {
                    is BodyInfoItem.ImageItem -> {
                        Box(
                            modifier = Modifier
                                .size(width = item.width, height = item.height)
                                .clip(shape)
                                .then(borderModifier)
                                .clickable {
                                    selectedIndex = index
                                    onItemSelected(index)
                                }
                        ) {
                            Image(
                                painter = item.painter,
                                contentDescription = "Body Info Image $index",
                                modifier = Modifier.fillMaxSize()
                            )
                        }
                    }
                    is BodyInfoItem.ColorItem -> {
                        Box(
                            modifier = Modifier
                                .size(35.dp)
                                .clip(shape)
                                .then(borderModifier)
                                .background(item.color)
                                .clickable {
                                    selectedIndex = index
                                    onItemSelected(index)
                                }
                        )
                    }
                }
            }
        }
    }
}


//preview
@Preview(showBackground = true)
@Composable
fun BodyInfoScrollerPreview() {
    val items = listOf(
        BodyInfoItem.ImageItem(painter = painterResource(id = R.drawable.ic_launcher_foreground)),
        BodyInfoItem.ColorItem(Color.Red),
        BodyInfoItem.ColorItem(Color.Green),
        BodyInfoItem.ColorItem(Color.Blue)
    )

    BodyInfoScroller(
        title = "Body Shapes",
        items = items,
        onItemSelected = { index -> println("Selected item index: $index") }
    )
}