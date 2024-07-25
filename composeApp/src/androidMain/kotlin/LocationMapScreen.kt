
import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Slider
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.model.AutocompletePrediction
import com.google.android.libraries.places.api.model.CircularBounds
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest
import com.google.android.libraries.places.api.net.PlacesClient
import com.google.maps.android.compose.Circle
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import kotlinx.coroutines.launch

@SuppressLint("UnrememberedMutableState")
@Composable
fun LocationMapScreen(placesClient: PlacesClient, placeIdState: MutableState<String>, navController: NavController,) {

    var query by remember { mutableStateOf("") }
    var predictions by remember { mutableStateOf(listOf<AutocompletePrediction>()) }
    var selectedLocation by remember { mutableStateOf<LatLng?>(null) }
    var radius by remember { mutableStateOf(1000.0) } // Initial radius value in meters
    val cameraPositionState = rememberCameraPositionState()

    val coroutineScope = rememberCoroutineScope()

    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp)) {
        TextField(
            value = query,
            onValueChange = {
                query = it
                if (query.isNotEmpty()) {
                    fetchPlacePredictions(query, placesClient) { fetchedPredictions ->
                        predictions = fetchedPredictions
                    }
                } else {
                    predictions = emptyList()
                }
            },
            placeholder = { Text("Search for a place") },
            textStyle = TextStyle(fontSize = 16.sp),
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White, RoundedCornerShape(8.dp))
                .padding(8.dp),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.White,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            )
        )

        if (predictions.isNotEmpty()) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(vertical = 8.dp)
            ) {
                items(predictions) { prediction ->
                    Text(
                        text = prediction.getFullText(null).toString(),
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                placeIdState.value = prediction.placeId
                                query = prediction.getFullText(null).toString()
                                fetchPlaceDetails(prediction.placeId, placesClient) { latLng ->
                                    selectedLocation = latLng
                                    coroutineScope.launch {
                                        cameraPositionState.animate(
                                            CameraUpdateFactory.newLatLngZoom(latLng, 15f)
                                        )
                                    }
                                    predictions = emptyList() // Clear predictions after selection
                                }
                            }
                            .padding(8.dp),
                        style = TextStyle(fontSize = 16.sp)
                    )
                }
            }
        }

        // Slider to adjust the radius
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Radius: ${radius.toInt()} meters", style = TextStyle(fontSize = 16.sp))
        Slider(
            value = radius.toFloat(),
            onValueChange = { radius = it.toDouble() },
            valueRange = 100.0f..5000.0f, // Radius range from 100 meters to 5000 meters
            modifier = Modifier.fillMaxWidth()
        )

        Box(modifier = Modifier.fillMaxSize()) {
            GoogleMap(
                modifier = Modifier.fillMaxSize(),
                cameraPositionState = cameraPositionState
            ) {
                selectedLocation?.let { location ->
                    Marker(
                        state = MarkerState(position = location),
                        title = "Selected Location",
                        snippet = query
                    )
                    Circle(
                        center = location,
                        radius = radius,
                        strokeColor = Color.Blue,
                        fillColor = Color(0x220000FF)
                    )
                }
            }
        }
        Button(
            onClick = { navController.navigate("dashboard_screen") },
            modifier = Modifier.padding(16.dp)
        ) {
            Text("Back to Create Alert")
        }
    }
}

fun fetchPlacePredictions(query: String, placesClient: PlacesClient, onPredictionsFetched: (List<AutocompletePrediction>) -> Unit) {
    val center = LatLng(37.7749, -122.4194) // Center of the search area
    val radius = 5000.0 // Radius in meters
    val circle = CircularBounds.newInstance(center, radius)

    val request = FindAutocompletePredictionsRequest.builder()
        .setQuery(query)
        .setRegionCode("US")
        .setLocationBias(circle)
        .build()

    placesClient.findAutocompletePredictions(request)
        .addOnSuccessListener { response ->
            val predictions = response.autocompletePredictions
            onPredictionsFetched(predictions)
        }
        .addOnFailureListener { exception ->
            Log.e("PlaceAutocomplete", "Some exception happened: ${exception.message}")
        }
}

fun fetchPlaceDetails(placeId: String, placesClient: PlacesClient, onPlaceDetailsFetched: (LatLng) -> Unit) {
    val request = com.google.android.libraries.places.api.net.FetchPlaceRequest.builder(placeId, listOf(com.google.android.libraries.places.api.model.Place.Field.LAT_LNG))
        .build()

    placesClient.fetchPlace(request)
        .addOnSuccessListener { response ->
            val place = response.place
            place.latLng?.let { latLng ->
                onPlaceDetailsFetched(latLng)
            }
        }
        .addOnFailureListener { exception ->
            Log.e("PlaceDetailsFetch", "Failed to fetch place details: ${exception.message}")
        }
}
