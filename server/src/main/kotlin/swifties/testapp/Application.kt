package swifties.testapp

import Greeting
import io.ktor.serialization.jackson.jackson
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.request.receive
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction
import swifties.testapp.LocationAlerts.locationName

fun main() {
    embeddedServer(Netty, port = 8080) {
        install(ContentNegotiation) {
            jackson()
        }
        Database.connect("postgresql://localhost:5432/postgres", driver = "org.postgresql.Driver", user = "root", password = "password")


        routing {
            route("/alerts") {
                post("/create") {
                    val alert = call.receive<LocationAlert>()
                    transaction {
                        LocationAlerts.insert {
                            it[locationName] = alert.locationName
                            it[latitude] = alert.latitude
                            it[longitude] = alert.longitude
                            it[radius] = alert.radius
                            it[message] = alert.message
                            it[active] = alert.active
                        }
                    }
                    call.respond("Alert created successfully")
                }

                get("/{id}") {
                    val id = call.parameters["id"]?.toIntOrNull()
                    if (id == null) {
                        call.respond("Invalid alert ID")
                        return@get
                    }
                    val alert = transaction {
                        LocationAlerts.select { LocationAlerts.alertId eq id }.map {
                            LocationAlert(
                                    alertId = it[LocationAlerts.alertId],
                                    locationName = it[LocationAlerts.locationName],
                                    latitude = it[LocationAlerts.latitude],
                                    longitude = it[LocationAlerts.longitude],
                                    radius = it[LocationAlerts.radius],
                                    message = it[LocationAlerts.message],
                                    active = it[LocationAlerts.active],
                                    createdAt = it[LocationAlerts.createdAt].toString()
                            )
                        }.singleOrNull()
                    }
                    if (alert == null) {
                        call.respond("Alert not found")
                    } else {
                        call.respond(alert)
                    }
                }

                put("/{id}") {
                    val id = call.parameters["id"]?.toIntOrNull()
                    if (id == null) {
                        call.respond("Invalid alert ID")
                        return@put
                    }
                    val alert = call.receive<LocationAlert>()
                    transaction {
                        LocationAlerts.update({ LocationAlerts.alertId eq id }) {
                            it[locationName] = alert.locationName
                            it[latitude] = alert.latitude
                            it[longitude] = alert.longitude
                            it[radius] = alert.radius
                            it[message] = alert.message
                            it[active] = alert.active
                        }
                    }
                    call.respond("Alert updated successfully")
                }

                delete("/{id}") {
                    val id = call.parameters["id"]?.toIntOrNull()
                    if (id == null) {
                        call.respond("Invalid alert ID")
                        return@delete
                    }
                    transaction {
                        LocationAlerts.deleteWhere { LocationAlerts.alertId eq id }
                    }
                    call.respond("Alert deleted successfully")
                }
            }
        }
    }.start(wait = true)
}

fun Application.module() {
    routing {
        get("/") {
            call.respondText("Ktor: ${Greeting().greet()}")
        }
    }
}