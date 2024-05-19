package swifties.testapp

data class LocationAlert(
        val alertId: Int,
        val locationName: String,
        val latitude: Double,
        val longitude: Double,
        val radius: Int,
        val message: String,
        val active: Boolean = true,
        val createdAt: String? = null
)