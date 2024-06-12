
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import swifties.testapp.R

@Composable
fun GradientImageView(modifier: Modifier = Modifier) {
    Image(
        painter = painterResource(id = R.drawable.welcome_page), // Replace with your image resource
        contentDescription = null,
        contentScale = ContentScale.Crop, // Scale the image to fill the size
        modifier = modifier.fillMaxSize()
    )
}

@Composable
fun SignUpScreen(
    switchToDashboard: (String) -> Unit,
    switchToLoginScreen: () -> Unit,
) {
    Box(modifier = Modifier.fillMaxSize()) {
        GradientImageView()

        UsernamePasswordAndButton(
            title = "Sign Up",
            buttonLabel = "Sign Up",
            onButtonClick = { email, password ->
                val loginResponse = RestAPIAccess.attemptSignup(email, password)
                switchToDashboard(loginResponse.jwt)
            }
        ) {
            switchCommand(
                label = "Already have an account?",
                clickableText = "Log in",
                onClick = switchToLoginScreen
            )
        }

        Spacer(modifier = Modifier.padding(top = 75.dp))
    }
}