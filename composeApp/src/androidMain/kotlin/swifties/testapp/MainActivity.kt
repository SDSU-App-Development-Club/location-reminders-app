package swifties.testapp

import App
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import com.google.android.libraries.places.api.Places

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val prefs = application.getSharedPreferences("prefs", MODE_PRIVATE)
        Places.initialize(applicationContext, "AIzaSyDR5tab5BEY3t5NzFSDsomEnN2QptmlJeo")
        setContent {
            MaterialTheme {
                App(prefs)
            }
        }
    }
}
