import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
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
import androidx.compose.foundation.layout.requiredSize
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

@Composable
fun DashboardScreen(userId: String) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        GradientImageView()
        ScheduleScreen()
    }
}

@Composable
fun ScheduleScreen() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        var showPopup by remember { mutableStateOf(false) }
        val showList = remember {
            MutableTransitionState(true)
        }

        Spacer(modifier = Modifier.height(50.dp))
        AnimatedVisibility(visibleState = showList) {
            Column(modifier = Modifier.padding(15.dp)) {
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

        Spacer(modifier = Modifier.height(if (showPopup) 0.dp else 400.dp))

        AnimatedContent(
            targetState = showPopup,
            transitionSpec = {
                val durationMillis = 500

                if (targetState != initialState && targetState) {
                    slideInVertically(animationSpec = tween(durationMillis)) { height -> height } togetherWith
                            slideOutVertically(animationSpec = tween(durationMillis)) { height -> -height }
                } else {
                    slideInVertically(animationSpec = tween(durationMillis)) { height -> -height } togetherWith
                            slideOutVertically(animationSpec = tween(durationMillis)) { height -> height }
                }.using(SizeTransform(clip = false))
            },
            label = "Add Alert Popup Menu",
            modifier = Modifier.fillMaxSize()
        ) { buttonClicked ->
            if (buttonClicked) {
                // Show entire menu
                Box {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(top = 32.dp)
                            .clip(RoundedCornerShape(topEnd = 20.dp))
                    ) {
                        Text(
                            "Some content",
                            modifier = Modifier
                                .background(Color.White)
                                .padding(top = 100.dp)
                                .fillMaxSize(),
                            fontSize = 26.sp
                        )
                    }
                    FloatingActionButton(
                        onClick = {
                            showPopup = false
                            showList.targetState = true
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
            } else {
                // Show minimized
                FloatingActionButton(
                    onClick = {
                        showPopup = true
                        showList.targetState = false
                    },
                    backgroundColor = Color.White,
                    contentColor = Color(0xFF4CAF50),
                    modifier = Modifier
                        .requiredSize(50.dp)
                        .align(Alignment.CenterHorizontally),
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Add Task")
                }
            }
        }
    }
}
