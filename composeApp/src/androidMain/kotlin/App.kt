import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.jetbrains.compose.ui.tooling.preview.Preview

const val SIGNUP_SCREEN_ROUTE = "signup_screen"
const val LOGIN_SCREEN_ROUTE = "login_screen"
const val DASHBOARD_SCREEN_ROUTE = "dashboard_screen"

@Composable
@Preview
fun App(navController: NavHostController = rememberNavController()) {
    NavHost(
        navController = navController,
        startDestination = SIGNUP_SCREEN_ROUTE,
        modifier = Modifier
            .fillMaxSize()
    ) {
        composable(SIGNUP_SCREEN_ROUTE) {
            SignUpScreen(
                { token -> navController.navigate("$DASHBOARD_SCREEN_ROUTE/$token") },
                { navController.navigate(LOGIN_SCREEN_ROUTE) },
            )
        }
        composable(LOGIN_SCREEN_ROUTE) {
            LogInScreen(
                { token -> navController.navigate("$DASHBOARD_SCREEN_ROUTE/$token") },
                { navController.navigate(SIGNUP_SCREEN_ROUTE) },
            )

        }
        composable("$DASHBOARD_SCREEN_ROUTE/{token}") { backStackEntry ->
            val userId: String = backStackEntry.arguments?.getString("token")!!
            DashboardScreen(userId)
        }
    }
}
