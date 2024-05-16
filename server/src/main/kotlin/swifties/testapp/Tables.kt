package swifties.testapp

import org.jetbrains.exposed.dao.IntIdTable

object LocationAlerts : IntIdTable() {
    object LocationAlerts : Table() {
        val alertId = integer("alert_id").autoIncrement().primaryKey()
        val locationName = varchar("location_name", 255)
        val latitude = double("latitude")
        val longitude = double("longitude")
        val radius = integer("radius")
        val message = text("message")
        val active = bool("active").default(true)
        val createdAt = datetime("created_at").defaultExpression(CurrentDateTime())
}
