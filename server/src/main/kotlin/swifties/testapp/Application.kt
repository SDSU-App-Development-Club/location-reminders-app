package swifties.testapp

import Greeting
import io.github.cdimascio.dotenv.Dotenv
import io.github.cdimascio.dotenv.dotenv
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.from
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
import org.slf4j.LoggerFactory
import io.ktor.client.*
import io.ktor.client.engine.java.*


val logger = LoggerFactory.getLogger("pingas")
val env = Dotenv.load()
fun main() {
    embeddedServer(Netty, port = 8080) {
        install(ContentNegotiation) {
            jackson()
        }
        val client = HttpClient(Java)
        val supabase: SupabaseClient
        // just for testing, points to a local docker instance
        try {
            supabase = createSupabaseClient(
                supabaseUrl = env.get("DB_URL"),
                supabaseKey = env.get("DB_PUBLIC_ANON_KEY")
            ) {
                install(Postgrest)
            }
            logger.info("Connected to Supabase")
        }
        catch(e: Exception) {
            logger.info("Cannot connect to Supabase", e)
            throw e
        }


        // make the table in Postgres
//        transaction {
//            SchemaUtils.create(AlertsTable)
//        }

        routing {
            route("/alerts") {
                post("/create") {
                    val alert = call.receive<LocationAlert>()
                    supabase.from("location_alerts").insert(alert)
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