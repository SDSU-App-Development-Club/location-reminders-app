package swifties.testapp

import Greeting
import SERVER_PORT
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun main() {
    embeddedServer(Netty, port = 8080) {
        install(ContentNegotiation) {
            gson {
                setPrettyPrinting()
            }
        }
        Database.connect("jdbc:postgresql://your-supabase-url:5432/postgres", driver = "org.postgresql.Driver", user = "your-supabase-user", password = "your-supabase-password")
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
                            it[createdAt] = DateTime.now()
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