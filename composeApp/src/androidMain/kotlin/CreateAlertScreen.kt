
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.emoji2.emojipicker.EmojiPickerView
import androidx.emoji2.emojipicker.EmojiViewItem

@Composable
fun CreateAlertScreen() {
    val taskName = remember { mutableStateOf("") }
    val notes = remember { mutableStateOf("") }
    var isEmojiPickerVisible by remember { mutableStateOf(false) }
    var selectedEmoji by remember { mutableStateOf(EmojiViewItem("", emptyList())) }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 32.dp)
                .padding(bottom = 32.dp)
                .padding(horizontal = 15.dp)
        ) {
            TextField(
                value = taskName.value,
                onValueChange = { taskName.value = it },
                textStyle = TextStyle(fontSize = 20.sp),
                modifier = Modifier
                    .weight(1f)
                    .background(Color.White, CircleShape)
                    .padding(8.dp),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.White,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
            )
            Button(onClick = { isEmojiPickerVisible = !isEmojiPickerVisible }) {
                if (selectedEmoji.emoji != "") {
                    Text(selectedEmoji.emoji)
                }
                else {
                    // Add default emoji icon inside text
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
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .background(Color.White, RoundedCornerShape(8.dp))
                    .padding(8.dp),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.White,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
            )
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
            .height(150.dp), // Set a height to ensure visibility
    )
}
