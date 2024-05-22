package swifties.testapp

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty

data class LocationAlert(
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    @JsonProperty("alert_id")
    val alertId: Int,
    @JsonProperty("location_name")
    val locationName: String,
    val latitude: Double,
    val longitude: Double,
    val radius: Int,
    val message: String,
    val active: Boolean = true,
    @JsonProperty("created_at")
    val createdAt: String? = null
)