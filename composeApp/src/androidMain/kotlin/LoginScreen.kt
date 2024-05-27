
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun LogInScreen() {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        GradientImageView()
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(25.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = "Sign Up",
                fontSize = 37.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            TextField(
                modifier = Modifier
                    .padding(top = 20.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .fillMaxWidth()
                    .background(color = Color.White),
                value = "",
                onValueChange = {},
                label = {
                    Text(text = "Email Address")
                }
            )
            TextField(
                modifier = Modifier
                    .padding(top = 20.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .fillMaxWidth()
                    .background(color = Color.White),
                value = "",
                onValueChange = {},
                label = {
                    Text(text = "Password")
                }
            )

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp)
                    .height(55.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(Color.White.copy(alpha = 0.8f))
            ) {
                // Overlay the gradient text on top of the button
                Text(
                    text = "Sign Up",
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Text(text = "Already have an account?", color = Color.White)
                Text(
                    text = "Log in",
                    fontWeight = FontWeight.Bold,
                    textDecoration = TextDecoration.Underline,
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.padding(top = 75.dp))
        }
    }
}