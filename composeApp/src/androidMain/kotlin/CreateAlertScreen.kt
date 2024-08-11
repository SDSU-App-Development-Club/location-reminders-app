import android.content.SharedPreferences
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.emoji2.emojipicker.EmojiPickerView
import androidx.emoji2.emojipicker.EmojiViewItem
import androidx.navigation.NavController
import com.google.android.libraries.places.api.net.PlacesClient
import swifties.testapp.R

@Composable
fun CreateAlertScreen(
    prefs: SharedPreferences,
    placesClient: PlacesClient,
    navController: NavController,
    titleState: MutableState<String>,
) {
    var isEmojiPickerVisible by remember { mutableStateOf(false) }
    var selectedEmoji by remember { mutableStateOf(EmojiViewItem("", emptyList())) }
    val selectedLocationState = remember { mutableIntStateOf(0) }
    val showMapPopupState = remember { mutableStateOf(false) }

    // main element container with padding on the sides
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 15.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        // used by .clickable to close emoji picker without picking an emoji
        val interactionSource = remember { MutableInteractionSource() }

        Column(
            modifier = Modifier
                .clickable(
                    interactionSource = interactionSource,
                    indication = null
                ) { isEmojiPickerVisible = false },
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Task name + emoji
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 32.dp)
            ) {
                Button(
                    modifier = Modifier
                        .padding(end = 10.dp)
                        .height(54.dp)
                        .width(54.dp)
                        .clip(CircleShape),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                    contentPadding = PaddingValues(0.dp),
                    onClick = { isEmojiPickerVisible = !isEmojiPickerVisible }
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.width(54.dp)
                    ) {
                        Text(
                            text = selectedEmoji.emoji,
                            style = TextStyle(
                                fontSize = 32.sp
                            )
                        )

                        if (selectedEmoji.emoji.isEmpty()) {
                            Icon(
                                painter = painterResource(R.drawable.baseline_add_reaction_24),
                                tint = Color(0xCB, 0xCB, 0xCB),
                                contentDescription = "ah",
                                modifier = Modifier.fillMaxSize(fraction = 0.6f)
                            )
                        }
                    }
                }

                val taskNameState = remember { mutableStateOf(if (titleState.value == "New Task") "" else titleState.value) }
                // Task name
                TextField(
                    value = taskNameState.value,
                    onValueChange = {
                        if (it.isEmpty()) {
                            titleState.value = "New Task"
                        } else {
                            titleState.value = it
                        }
                        taskNameState.value = it
                    },
                    colors = TextFieldDefaults.colors(
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    singleLine = true,
                    placeholder = { Text("New Task") },
                    textStyle = TextStyle(fontSize = 20.sp),
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(30.dp)),
                )
            }

            // hide everything below the emoji button so that it can be replaced by emoji picker
            if (!isEmojiPickerVisible) {
                NotesTextBox()

                val placeIdState = remember { mutableStateOf("") }

                LocationComponent(selectedLocationState, placeIdState, placesClient, prefs, showMapPopupState)
            }
        }
        if (isEmojiPickerVisible) {
            EmojiPicker(
                onEmojiSelected = { emoji ->
                    selectedEmoji = emoji
                    isEmojiPickerVisible = false // Close picker after selection
                }
            )
        }
    }
}

@Composable
private fun NotesTextBox() {
    val notesState = remember { mutableStateOf("") }

    // Notes
    TextField(
        value = notesState.value,
        onValueChange = {
            // limit to 280 chars
            if (it.codePointCount(0, it.length) <= 280) {
                notesState.value = it
            }
        },
        textStyle = TextStyle(fontSize = 16.sp),
        placeholder = { Text("Notes") },
        maxLines = 5,
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = Color.White,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ),
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(30.dp)),
    )
}

@Composable
fun EmojiPicker(onEmojiSelected: (EmojiViewItem) -> Unit) {
    AndroidView(
        factory = { context ->
            EmojiPickerView(context).apply {
                setOnEmojiPickedListener { emoji ->
                    onEmojiSelected(emoji)
                }
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxSize()
    )
}

@Composable
fun LocationComponent(
    selectedLocationState: MutableIntState,
    placeIdState: MutableState<String>,
    placesClient: PlacesClient,
    prefs: SharedPreferences,
    showMapPopupState: MutableState<Boolean>
) {
    var selectedLocation by selectedLocationState
    var placeId by placeIdState
    var showMapPopup by showMapPopupState

    Column(
        modifier = Modifier
            .background(Color.White, shape = RoundedCornerShape(30.dp))
            .padding(vertical = 16.dp, horizontal = 14.dp)
    ) {
        // Row for the icons and text
        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            modifier = Modifier.fillMaxWidth()
        ) {
            val labels = arrayOf("Current", "Home", "Work", "Custom")
            val icons = intArrayOf(
                R.drawable.baseline_assistant_navigation_24,
                R.drawable.baseline_add_home_24,
                R.drawable.baseline_apartment_24,
                R.drawable.baseline_edit_24
            )

            for (i in 0 until 4) {
                val label = labels[i]
                val buttonColor = if (selectedLocation == i) Color(48, 185, 0) else Color.LightGray
                val textColor = if (selectedLocation == i) Color(48, 185, 0) else Color.Black

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Button(
                        onClick = {
                            selectedLocation = i
                            placeId = prefs.getString(label, "")!!
                            // only show popup for custom location choice
                            showMapPopup = i == 3
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = buttonColor,
                            contentColor = Color.White
                        ),
                        contentPadding = PaddingValues(5.dp),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier
                            .size(64.dp)
                            .clip(RectangleShape)
                    ) {
                        Image(
                            painter = painterResource(icons[i]),
                            contentDescription = label,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                    Text(
                        text = label,
                        style = TextStyle(fontSize = 12.sp, fontWeight = FontWeight.SemiBold),
                        color = textColor,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))
        HorizontalDivider()

        // Arriving text
        Text(
            text = "Arriving: " + placeIdState.value,
            style = MaterialTheme.typography.bodyMedium,
            color = Color.DarkGray,
            modifier = Modifier.padding(start = 6.dp, top = 4.dp)
        )
    }

    LocationMapScreen(prefs, placesClient, placeIdState)
}
