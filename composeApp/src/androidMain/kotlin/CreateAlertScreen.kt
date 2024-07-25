
import android.content.SharedPreferences
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
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
    val interactionSource = remember { MutableInteractionSource() }
    val taskName = remember { mutableStateOf("") }
    val notes = remember { mutableStateOf("") }
    var isEmojiPickerVisible by remember { mutableStateOf(false) }
    var selectedEmoji by remember { mutableStateOf(EmojiViewItem("", emptyList())) }
    val selectedLocationState = remember { mutableIntStateOf(0) }
    var selectedLocation by selectedLocationState
    val placeIdState = remember { mutableStateOf("") }
    var placeId by placeIdState
    var showMapPopupState = remember { mutableStateOf(false) }
    var showMapPopup by showMapPopupState

    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
       /* Column(
            modifier = Modifier
                .clickable (
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
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color.White,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    )
                )
                Button(
                    modifier = Modifier.padding(5.dp, 0.dp, 0.dp, 0.dp),
                    shape = CircleShape,
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
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
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color.White,
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
        } */
        LocationMapScreen(placesClient, placeIdState, navController)
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
            LocationItem(
                iconRes = R.drawable.baseline_assistant_navigation_24,
                label = "Current",
                i = 0,
                selectedLocationState,
                placeIdState,
                prefs,
                showMapPopupState
            )
            LocationItem(
                iconRes = R.drawable.baseline_add_home_24,
                label = "Home",
                i = 1,
                selectedLocationState,
                placeIdState,
                prefs,
                showMapPopupState
            )
            LocationItem(
                iconRes = R.drawable.baseline_apartment_24,
                label = "Work",
                i = 2,
                selectedLocationState,
                placeIdState,
                prefs,
                showMapPopupState
            )
            LocationItem(
                iconRes = R.drawable.baseline_edit_24,
                label = "Custom",
                i = 3,
                selectedLocationState,
                placeIdState,
                prefs,
                showMapPopupState
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Arriving text
        Text(
            text = placeIdState.value,
            style = MaterialTheme.typography.bodySmall,
            color = Color.Gray
        )
    }
}

@Composable
fun LocationItem(
    iconRes: Int,
    label: String,
    i: Int,
    selectedLocationState: MutableIntState,
    placeIdState: MutableState<String>,
    prefs: SharedPreferences,
    showMapPopupState: MutableState<Boolean>
) {
    var isSelected by selectedLocationState
    Box()
    {
        Button(
            modifier = Modifier
                .size(60.dp),
            onClick = {
                isSelected = i;
                placeIdState.value = prefs.getString(label, "") ?: "";
                showMapPopupState.value = !showMapPopupState.value;
            },
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color.Transparent,
                contentColor = Color.Transparent
            ),

        ) {}

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .background(
                    if (isSelected == i) Color(53, 150, 59) else Color.LightGray,
                    shape = RoundedCornerShape(8.dp)
                )
                .width(60.dp) // Adjust the width as needed
                .height(60.dp)
        ) {
            Image(
                painter = painterResource(id = iconRes),
                contentDescription = label,
                modifier = Modifier
                    .size(40.dp) // Adjust the size as needed
            )
            Text(
                text = label,
                style = TextStyle(fontSize = 12.sp), // Adjust the font size as needed
                color = if (isSelected == i) Color.White else Color.Black, // Ensure contrasting color
                modifier = Modifier.padding(top = 4.dp) // Adjust the padding as needed
            )
        }
    }

}


