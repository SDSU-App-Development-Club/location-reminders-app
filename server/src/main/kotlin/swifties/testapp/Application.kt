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
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update

fun main() {
    embeddedServer(Netty, port = 8080) {
        install(ContentNegotiation) {
            jackson()
        }

        // just for testing, points to a local docker instance
        Database.connect("jdbc:postgresql://127.0.0.1:5432/alerts", driver = "org.postgresql.Driver", user = "root", password = "password")

        // make the table in Postgres
        transaction {
            SchemaUtils.create(AlertsTable)
        }

        routing {
            route("/alerts") {
                post("/create") {
                    val alert = call.receive<LocationAlert>()
                    transaction {
                        AlertsTable.insert {
                            it[id] = alert.alertId
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
                        AlertsTable.selectAll().where { AlertsTable.id eq id }.map {
                            LocationAlert(
                                    alertId = it[AlertsTable.id].value,
                                    locationName = it[AlertsTable.locationName],
                                    latitude = it[AlertsTable.latitude],
                                    longitude = it[AlertsTable.longitude],
                                    radius = it[AlertsTable.radius],
                                    message = it[AlertsTable.message],
                                    active = it[AlertsTable.active],
                                    createdAt = it[AlertsTable.createdAt].toString()
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
                        AlertsTable.update({ AlertsTable.id eq id }) {
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
                        AlertsTable.deleteWhere { AlertsTable.id eq id }
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