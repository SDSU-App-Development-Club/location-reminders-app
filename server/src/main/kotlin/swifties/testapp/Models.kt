package swifties.testapp

import kotlinx.serialization.Serializable

@Serializable
data class LocationAlert(
        val alert_id: Int,
        val location_name: String,
        val latitude: Double,
        val longitude: Double,
        val radius: Int,
        val message: String,
        val active: Boolean = true,
        val created_at: String? = null
)