
import android.content.SharedPreferences
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults.buttonColors
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.FloatingActionButtonDefaults.elevation
import androidx.compose.material.Icon
import androidx.compose.material.TabRowDefaults.Divider
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.android.libraries.places.api.net.PlacesClient
import swifties.testapp.R

@Composable
fun DashboardScreen(prefs: SharedPreferences, placesClient: PlacesClient, navController: NavController) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        GradientImageView(resourceId = R.drawable.dashboard_page)
        ScheduleScreen(prefs, placesClient, navController)
    }
}

@Composable
fun ScheduleScreen(
    prefs: SharedPreferences,
    placesClient: PlacesClient,
    navController: NavController
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        var showPopup by remember { mutableStateOf(false) }

        AnimatedVisibility(
            visible = !showPopup,
            enter = fadeIn(),
            exit = fadeOut(),
        ) {
            Column(modifier = Modifier.padding(15.dp)) {
                Spacer(modifier = Modifier.height(50.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(
                        text = "Today",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        modifier = Modifier.alignByBaseline()
                    )
                }
                Divider(
                    color = Color.White,
                    thickness = 1.dp,
                    modifier = Modifier
                        .padding(vertical = 8.dp)
                        .fillMaxWidth(0.9f)
                )
                Spacer(modifier = Modifier.height(60.dp))
                Text(
                    text = "No new reminders",
                    fontSize = 18.sp,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    shape = RoundedCornerShape(20.dp),
                    onClick = { /* Handle new task creation */ },
                    colors = buttonColors(backgroundColor = Color.White),
                ) {
                    Text(text = "+ New Task", color = Color.Black)
                }
            }
        }

        // Column of spacer and the popup menu
        Column {
            val animatedHeight by animateDpAsState(
                targetValue = if (showPopup) 40.dp else 680.dp
            )

            // Large space keeps menu minimized
            Spacer(modifier = Modifier.height(animatedHeight))

            // Box allows overlapping the
            Box(modifier = Modifier
                .fillMaxSize()) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 32.dp)
                        .clip(RoundedCornerShape(topEnd = 20.dp))
                        .background(Color(235, 235, 235))
                ) {
                    Text(
                        "New Alert",
                        modifier = Modifier
                            .background(Color.White)
                            .padding(top = 8.dp, start = 72.dp)
                            .fillMaxWidth(),
                        fontSize = 28.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF009a88),

                    )

                    CreateAlertScreen(prefs, placesClient, navController)
                }
                FloatingActionButton(
                    onClick = {
                        showPopup = !showPopup
                    },
                    contentColor = Color.White,
                    backgroundColor = Color(0xFF009a88),
                    elevation = elevation(0.dp, 0.dp),
                    shape = CircleShape,
                    modifier = Modifier
                        .size(64.dp)
                        .border(6.dp, Color.White, CircleShape),
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Add Task")
                }
            }
        }
    }
}
