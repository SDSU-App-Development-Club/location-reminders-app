package swifties.testapp

import App
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val prefs = application.getSharedPreferences("prefs", MODE_PRIVATE)

        setContent {
            MaterialTheme {
                App(prefs)
            }
        }
    }
}
