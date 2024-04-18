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
    CenteredColumn {
        Text("Create Account")
    }
}
