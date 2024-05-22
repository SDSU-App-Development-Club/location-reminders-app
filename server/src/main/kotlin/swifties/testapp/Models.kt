package swifties.testapp

import kotlinx.serialization.EncodeDefault
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable

@Serializable
@OptIn(ExperimentalSerializationApi::class)
data class LocationAlert constructor(
    @EncodeDefault(EncodeDefault.Mode.NEVER)
    val alert_id: Int = 0,
    val location_name: String,
    val latitude: Double,
    val longitude: Double,
    val radius: Int,
    val message: String,
    val active: Boolean = true,
    val created_at: String? = null
)