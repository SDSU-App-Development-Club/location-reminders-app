package swifties.testapp

import App
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import com.google.android.libraries.places.api.Places

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val prefs = application.getSharedPreferences("prefs", MODE_PRIVATE)

        val applicationInfo = packageManager.getApplicationInfo(packageName, PackageManager.GET_META_DATA)
        val apiKey = applicationInfo.metaData.getString("com.google.android.geo.API_KEY")
        Places.initializeWithNewPlacesApiEnabled(applicationContext, apiKey!!)
        val placesClient = Places.createClient(this)

        setContent {
            MaterialTheme {
                App(prefs, placesClient)
            }
        }
    }
}
