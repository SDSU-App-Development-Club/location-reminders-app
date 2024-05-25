
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import swifties.testapp.R

val provider = GoogleFont.Provider(
    providerAuthority = "com.google.android.gms.fonts",
    providerPackage = "com.google.android.gms",
    certificates = R.array.com_google_android_gms_fonts_certs
)
val fontName = GoogleFont("Arial")

val fontFamily = FontFamily(
    Font(googleFont = fontName, fontProvider = provider)
)
@Composable
fun SignUpScreen(){

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(25.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start
    ){
        Text(text = "Sign Up",
            fontSize = 37.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = fontFamily
        )
        TextField(modifier = Modifier
            .padding(top = 20.dp)
            .clip(RoundedCornerShape(20.dp))
            .fillMaxWidth(),
            value = "",
            onValueChange = {},
            label = {
                Text(text = "Email Address")
            }
        )
        TextField(modifier = Modifier
            .padding(top = 20.dp)
            .clip(RoundedCornerShape(20.dp))
            .fillMaxWidth(),
            value = "",
            onValueChange = {},
            label = {
                Text(text = "Password")
            }
        )
        Button(modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp)
            .height(55.dp)
            .clip(RoundedCornerShape(20.dp)),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color.Black),
            onClick = { /*TODO*/ }
        ) {
            Text(
                text = "Sign Up",
                color = Color.Transparent
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth()
                    .padding(top = 10.dp),
            horizontalArrangement = Arrangement.spacedBy(6.dp)

        ) {
            Text(text = "Already have an account?")
            Text(
                text = "Log in",
                fontWeight = FontWeight.Bold,
                textDecoration = TextDecoration.Underline)
        }

        Spacer(modifier = Modifier.padding(top = 75.dp))
    }


}