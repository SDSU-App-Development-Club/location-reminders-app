package swifties.testapp

import kotlinx.serialization.Serializable

@Serializable
data class LocationAlert(
    val alert_id: Int? = null,
    val location_name: String,
    val latitude: Double,
    val longitude: Double,
    val radius: Int,
    val message: String,
    val active: Boolean,
    val created_at: String // ISO 8601 format timestamp without time zone
)
