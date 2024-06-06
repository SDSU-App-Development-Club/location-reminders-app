
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
/*
        var emailField by remember { mutableStateOf("") }
        var passwordField by remember { mutableStateOf("") }

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
                value = emailField,
                onValueChange = { emailField = it },
                label = { Text(text = "Email Address") }
            )
            TextField(
                modifier = Modifier
                    .padding(top = 20.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .fillMaxWidth()
                    .background(color = Color.White),
                value = passwordField,
                onValueChange = { passwordField = it },
                label = { Text(text = "Enter Password") },
                visualTransformation = PasswordVisualTransformation(),
            )

            val buttonContext = rememberCoroutineScope()

            Button(
                onClick = {
                    buttonContext.launch {
                        val loginResponse = RestAPIAccess.attemptSignup(emailField, passwordField)
                        switchToDashboard(loginResponse.jwt)
                    }
                },
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
        }*/

        Spacer(modifier = Modifier.padding(top = 75.dp))
    }
}