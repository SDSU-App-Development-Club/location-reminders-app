package swifties.testapp

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.kotlin.datetime.CurrentDateTime
import org.jetbrains.exposed.sql.kotlin.datetime.datetime

object LocationAlerts : IntIdTable(columnName = "alert_id") {
    val locationName = varchar("location_name", 255)
    val latitude = double("latitude")
    val longitude = double("longitude")
    val radius = integer("radius")
    val message = text("message")
    val active = bool("active").default(true)
    val createdAt = datetime("created_at").defaultExpression(CurrentDateTime)
}
