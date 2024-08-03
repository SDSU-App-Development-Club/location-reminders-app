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
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.layout.ContentScale
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
    navController: NavController
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .background(Color.White)
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .fillMaxHeight()
        ) {
            Text(
                text = "New Task #?",
                style = TextStyle(
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 24.sp
                )
            )
        }
        HorizontalDivider(thickness = 2.dp)
    }

    val interactionSource = remember { MutableInteractionSource() }
    val taskName = remember { mutableStateOf("") }
    val notes = remember { mutableStateOf("") }
    var isEmojiPickerVisible by remember { mutableStateOf(false) }
    var selectedEmoji by remember { mutableStateOf(EmojiViewItem("", emptyList())) }
    val selectedLocationState = remember { mutableIntStateOf(0) }
    var selectedLocation by selectedLocationState
    val placeIdState = remember { mutableStateOf("") }
    var placeId by placeIdState
    val showMapPopupState = remember { mutableStateOf(false) }
    var showMapPopup by showMapPopupState

    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Column(
            modifier = Modifier
                .clickable(
                    interactionSource = interactionSource,
                    indication = null
                ) { isEmojiPickerVisible = false }

        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 32.dp)
                    .padding(horizontal = 15.dp, vertical = 15.dp)
            ) {
                TextField(
                    value = taskName.value,
                    onValueChange = { taskName.value = it },
                    placeholder = { Text("New Task") },
                    textStyle = TextStyle(fontSize = 20.sp),
                    modifier = Modifier
                        .weight(1f)
                        .background(Color.White, RoundedCornerShape(8.dp))
                        .padding(8.dp),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    )
                )
                Button(
                    modifier = Modifier.padding(5.dp, 0.dp, 0.dp, 0.dp),
                    shape = CircleShape,
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                    onClick = { isEmojiPickerVisible = !isEmojiPickerVisible }) {
                    if (selectedEmoji.emoji != "") {
                        // Handle the case where an emoji is selected, if necessary
                        Text(selectedEmoji.emoji)
                    } else {
                        Image(
                            painter = painterResource(R.drawable.baseline_add_reaction_24),
                            contentDescription = "",
                            contentScale = ContentScale.Fit, // Try different ContentScale options
                        )
                    }
                }
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 15.dp)
            ) {
                TextField(
                    value = notes.value,
                    onValueChange = { notes.value = it },
                    textStyle = TextStyle(fontSize = 16.sp),
                    placeholder = { Text("Notes") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(75.dp)
                        .background(Color.White, RoundedCornerShape(8.dp))
                        .padding(8.dp),
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = Color.White,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    )
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 15.dp)
            ) {
            }

            LocationComponent(selectedLocationState, placeIdState, prefs, showMapPopupState)
        }
        if (isEmojiPickerVisible) {
            EmojiPicker(
                onEmojiSelected = { emoji ->
                    selectedEmoji = emoji
                    isEmojiPickerVisible = false // Close picker after selection
                }
            )
        } else {
            LocationMapScreen(prefs, placesClient, placeIdState, navController)
        }
    }
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
    prefs: SharedPreferences,
    showMapPopupState: MutableState<Boolean>
) {
    var selectedLocation by selectedLocationState
    var placeId by placeIdState
    var showMapPopup by showMapPopupState

    Column(
        modifier = Modifier
            .padding(16.dp)
            .background(Color.White, shape = RoundedCornerShape(8.dp))
            .padding(16.dp)
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
                val buttonColor = if (selectedLocation == i) Color(53, 150, 59) else Color.LightGray
                val textColor = if (selectedLocation == i) Color(53, 150, 59) else Color.Black

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

        Spacer(modifier = Modifier.height(16.dp))
        HorizontalDivider()


        // Arriving text
        Text(
            text = placeIdState.value,
            style = MaterialTheme.typography.bodySmall,
            color = Color.Gray
        )
    }
}
