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
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults.buttonColors
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults.textFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import swifties.testapp.R

@Composable
fun LogInScreen(
    switchToDashboard: (String) -> Unit,
    switchToSignUpScreen: () -> Unit,
) {
    Box(modifier = Modifier.fillMaxSize()) {
        GradientImageView(resourceId = R.drawable.login_page)
        UsernamePasswordAndButton(
            title = "Login",
            buttonLabel = "Login",
            onButtonClick = { email, password ->
                val loginResponse = RestAPIAccess.attemptLogin(email, password)

                if (loginResponse.ok) {
                    switchToDashboard(loginResponse.value().jwt)
                }
                else {

                }

            }
        ) {
            SwitchCommand(
                label = "Don't have an account?",
                clickableText = "Sign up",
                onClick = switchToSignUpScreen
            )
        }

        Spacer(modifier = Modifier.padding(top = 75.dp))
    }
}

/**
 * Template for login screen, shares common fields with Signup screen.
 *
 * @param title         Title shown at the top of the screen
 * @param beforeFields  Composable to add content before the Email and Password fields.
 * @param content   Composable to add content (ex. "Already have an account") after the Button.
 * @param buttonLabel   The text inside the button (ex. "Sign Up")
 * @param onButtonClick Button callback with the email and password contents.
 */
@Composable
fun UsernamePasswordAndButton(
    title: String,
    beforeFields: @Composable () -> Unit = {},
    buttonLabel: String,
    onButtonClick: suspend (String, String) -> Unit,
    content: @Composable () -> Unit,
) {
    var emailField by remember { mutableStateOf("") }
    var passwordField by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(25.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start
    ) {
        // Top left screen label
        Text(
            text = title,
            fontSize = 37.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )

        // Extra fields in case Sign Up takes more fields
        beforeFields()

        val teal = Color(0xFF009a88)
        val textFieldColors = textFieldColors(
            focusedIndicatorColor = teal,
            focusedLabelColor = teal,
            cursorColor = teal,
        )

        // Email field
        TextField(
            modifier = Modifier
                .padding(top = 20.dp)
                .clip(RoundedCornerShape(20.dp))
                .fillMaxWidth()
                .background(color = Color.White),
            value = emailField,
            onValueChange = { emailField = it },
            colors = textFieldColors,
            label = { Text(text = "Email Address") }
        )
        // Password field
        TextField(
            modifier = Modifier
                .padding(top = 20.dp)
                .clip(RoundedCornerShape(20.dp))
                .fillMaxWidth()
                .background(color = Color.White),
            value = passwordField,
            onValueChange = { passwordField = it },
            colors = textFieldColors,
            label = { Text(text = "Enter Password") },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        )

        // Sign in button
        val buttonContext = rememberCoroutineScope()
        Button(
            onClick = {
                buttonContext.launch { onButtonClick(emailField, passwordField) }
            },
            colors = buttonColors(
                backgroundColor = Color.White,
                contentColor = teal,
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp)
                .height(55.dp)
                .clip(RoundedCornerShape(20.dp))
        ) {
            // Overlay the gradient text on top of the button
            Text(
                text = buttonLabel,
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold
            )
        }

        // Extra lines after button (Already have an account?)
        content()
    }
}

@Composable
fun SwitchCommand(label: String, clickableText: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp),
        horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Text(
            text = label,
            fontSize = 18.sp,
            color = Color.White,
        )
        ClickableText(
            text = buildAnnotatedString {
                pushStyle(
                    SpanStyle(
                        fontWeight = FontWeight.Bold,
                        textDecoration = TextDecoration.Underline,
                        color = Color.White,
                        fontSize = 18.sp
                    )
                )
                append(clickableText)
                pop()
            },
            onClick = { onClick() }
        )
    }
}
