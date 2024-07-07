import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CreateAlertScreen() {
    Text(
        "Some content",
        color = Color.Black,
        modifier = Modifier
            .background(Color.White)
            .padding(top = 10.dp)
            .fillMaxSize(),
        fontSize = 26.sp
    )
}