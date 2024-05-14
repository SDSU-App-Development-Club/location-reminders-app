import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import testapp.composeapp.generated.resources.Res
import testapp.composeapp.generated.resources.compose_multiplatform

enum class MenuScreen() {
    LoginOrRegister,
    Login,
    CreateAccount,
}

@Composable
@Preview
fun App(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = MenuScreen.LoginOrRegister.name,
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        composable(route = MenuScreen.LoginOrRegister.name) {
            LoginRegisterScreen(
                onLoginClick = { navController.navigate(MenuScreen.Login.name) },
                onRegisterClick = { navController.navigate(MenuScreen.CreateAccount.name) }
            )
        }
        composable(route = MenuScreen.Login.name) {
            Text("hi")
        }
        composable(route = MenuScreen.CreateAccount.name) {
            CreateAccountScreen()
        }
    }
}

@Composable
@Preview
fun LoginRegisterScreen(
    onLoginClick: () -> Unit,
    onRegisterClick: () -> Unit,
) {
    CenteredColumn {
        CenteredColumn(modifier = Modifier.background(Color(0xffebebeb)).padding(vertical = 10.dp)) {
            Text("Mobile Test", style = TextStyle(
                fontSize = 42.sp,
                fontWeight = FontWeight.Bold
            ))
            Spacer(modifier = Modifier.height(64.dp))
            Button(onClick = onLoginClick, shape = RectangleShape, modifier = Modifier.width(100.dp)) {
                Text("Login")
            }
            Button(onClick = onRegisterClick, shape = RectangleShape, modifier = Modifier.width(100.dp)) {
                Text("Register")
            }
        }
    }
}

@Composable
private inline fun CenteredColumn(modifier: Modifier = Modifier, content: @Composable ColumnScope.() -> Unit) = Column(
    modifier = modifier
        .fillMaxWidth()
        .fillMaxSize(),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.Center,
    content = content,
)

@Composable
@Preview
fun CreateAccountScreen() {
    CenteredColumn(modifier = Modifier.width(100.dp).height(100.dp)) {
        // code from
        // https://developers.google.com/maps/documentation/android-sdk/maps-compose
        val sdsu = LatLng(32.774799, -117.071869)
        val cameraPositionState = rememberCameraPositionState {
            position = CameraPosition.fromLatLngZoom(sdsu, 10f)
        }

        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState
        ) {
            Marker(
                state = MarkerState(position = sdsu),
                title = "SDSU",
                snippet = "Marker in Singapore"
            )
        }
    }
}
