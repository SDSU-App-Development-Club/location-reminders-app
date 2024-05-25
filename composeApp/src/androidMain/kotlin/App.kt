
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import org.jetbrains.compose.ui.tooling.preview.Preview

enum class MenuScreen() {
    LoginOrRegister,
    Login,
    SignUpScreen,
}

@Composable
@Preview
fun App(
    navController: NavHostController = rememberNavController()
) {
    SignUpScreen()
    /*NavHost(
        navController = navController,
        startDestination = MenuScreen.LoginOrRegister.name,
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        *//*composable(route = MenuScreen.LoginOrRegister.name) {
            LoginRegisterScreen(
                onLoginClick = { navController.navigate(MenuScreen.Login.name) },
                onRegisterClick = { navController.navigate(MenuScreen.CreateAccount.name) }
            )
        }*//*
        composable(route = MenuScreen.CreateAccount.name) {

        }
    }*/
}