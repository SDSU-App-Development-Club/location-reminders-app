
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
//    NavHost(
//        navController = navController,
//        startDestination = MenuScreen.SignUpScreen.name,
//        modifier = Modifier
//            .fillMaxSize()
//            .verticalScroll(rememberScrollState())
//    ) {
//        composable(route = MenuScreen.SignUpScreen.name) {
//            StartOrderScreen(
//                quantityOptions = DataSource.quantityOptions,
//                modifier = Modifier
//                    .fillMaxSize()
//                    .padding(dimensionResource(R.dimen.padding_medium))
//            )
//
//        }
//
//    }
}