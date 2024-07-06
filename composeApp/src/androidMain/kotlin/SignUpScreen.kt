import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import swifties.testapp.R

@Composable
fun GradientImageView(modifier: Modifier = Modifier, resourceId: Int) {
    Image(
        painter = painterResource(id = resourceId), // Replace with your image resource
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
        GradientImageView(resourceId = R.drawable.signup_page)

        UsernamePasswordAndButton(
            title = "Sign Up",
            buttonLabel = "Sign Up",
            onButtonClick = { email, password ->
                val loginResponse = RestAPIAccess.attemptSignup(email, password)
                switchToDashboard(loginResponse.jwt)
            }
        ) {
            SwitchCommand(
                label = "Already have an account?",
                clickableText = "Log in",
                onClick = switchToLoginScreen
            )
        }

        Spacer(modifier = Modifier.padding(top = 75.dp))
    }
}