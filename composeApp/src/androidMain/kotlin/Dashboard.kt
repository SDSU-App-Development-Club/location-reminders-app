import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.animation.with
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.ButtonDefaults.buttonColors
import androidx.compose.material.FloatingActionButton
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun DashboardScreen(userId: String) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        GradientImageView()
        ScheduleScreen()
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ScheduleScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),

        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(20.dp))
        DaysOfWeekRow()
        Spacer(modifier = Modifier.height(32.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
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
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
        ) {
            Text(text = "+ New Task", color = Color.Black)
        }
        Spacer(modifier = Modifier.weight(1f))

        var isClicked by remember { mutableStateOf(false) }

        AnimatedContent(
            targetState = isClicked,
            transitionSpec = {
                val durationMillis = 1000

                if (targetState != initialState && targetState) {
                    slideInVertically(animationSpec = tween(durationMillis)) { height -> height } togetherWith
                            slideOutVertically(animationSpec = tween(durationMillis)) { height -> -height }
                } else {
                    slideInVertically(animationSpec = tween(durationMillis)) { height -> -height } togetherWith
                            slideOutVertically(animationSpec = tween(durationMillis)) { height -> height }
                }.using(SizeTransform(clip = false))
            }, label = "Add Alert Popup Menu"
        ) { buttonClicked ->
            if (buttonClicked) {
                Column(Modifier.fillMaxSize()) {
                    Text(
                        "Some content",
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.White)
                            .padding(top = 100.dp),
                        fontSize = 26.sp
                    )
                }
            } else {
                // Add Button at bottom of screen
                FloatingActionButton(
                    onClick = { isClicked = true },
                    backgroundColor = Color.White,
                    contentColor = Color(0xFF4CAF50),
                    modifier = Modifier
                        .padding(16.dp)
                        .background(Color.White)
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Add Task")
                }
            }
        }
    }
}

@Composable
fun DaysOfWeekRow() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .background(color = Color.DarkGray),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        val daysOfWeek = listOf("S", "M", "T", "W", "T", "F", "S")
        daysOfWeek.forEachIndexed { index, day ->
            DayItem(day, index + 1)
        }
    }
}

@Composable
fun DayItem(day: String, date: Int) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = day,
            color = Color.White,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))
        Box(
            modifier = Modifier
                .size(36.dp)
                .background(Color.White, shape = CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = date.toString(),
                color = Color.Black,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}


