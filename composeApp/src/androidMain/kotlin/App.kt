import android.content.SharedPreferences
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.android.libraries.places.api.net.PlacesClient
import org.jetbrains.compose.ui.tooling.preview.Preview

const val SIGNUP_SCREEN_ROUTE = "signup_screen"
const val LOGIN_SCREEN_ROUTE = "login_screen"
const val DASHBOARD_SCREEN_ROUTE = "dashboard_screen"

@Composable
@Preview
fun App(prefs: SharedPreferences, placesClient: PlacesClient) {
    val navController: NavHostController = rememberNavController()
    val storedJwt = prefs.getString("jwt", null)

    RestAPIAccess.invalidJwtCallback = {
        // go back to login screen
        navController.navigate(LOGIN_SCREEN_ROUTE)

        // delete the expired token
        prefs.edit()
            .remove("jwt")
            .apply()
    }

    NavHost(
        navController = navController,
        startDestination = SIGNUP_SCREEN_ROUTE,
        modifier = Modifier.fillMaxSize()
    ) {
        composable(SIGNUP_SCREEN_ROUTE) {
            SignUpScreen(goToDashboard(prefs, navController)) { navController.navigate(LOGIN_SCREEN_ROUTE) }
        }
        composable(LOGIN_SCREEN_ROUTE) {
            LogInScreen(goToDashboard(prefs, navController)) { navController.navigate(SIGNUP_SCREEN_ROUTE) }
        }
        composable("$DASHBOARD_SCREEN_ROUTE/{token}") {
            DashboardScreen(prefs, placesClient, navController)
        }
    }

    // todo add check to see if token is expired (should probably use a new endpoint)
    if (storedJwt != null) {
        navController.navigate("$DASHBOARD_SCREEN_ROUTE/$storedJwt")
    }
}

private fun goToDashboard(prefs: SharedPreferences, navController: NavHostController) = { token: String ->
    navController.navigate("$DASHBOARD_SCREEN_ROUTE/$token")
    prefs.edit()
        .putString("jwt", token)
        .apply()
}
