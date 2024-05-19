package swifties.testapp

import Greeting
import io.github.cdimascio.dotenv.Dotenv
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
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.transactions.transaction
import org.slf4j.LoggerFactory
import io.ktor.client.*
import io.ktor.client.engine.java.*
import javax.management.Query.eq
import javax.xml.stream.Location


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
        } catch (e: Exception) {
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
                    try {
                        val alert = call.receive<LocationAlert>()
                        supabase.from("location_alerts").insert(alert)
                        call.respond("Alert created successfully")
                    } catch (e: Exception) {
                        logger.info("Cannot create alert: $e")
                    }
                }

                get("/get-alert/{id}") {
                    val id = call.parameters["id"]?.toIntOrNull()
                    if (id == null) {
                        call.respond("Invalid alert ID")
                        return@get
                    }
                    try {
                        val alert = supabase.from("location_alerts").select() {
                            filter {
                                LocationAlert::alert_id eq id
                            }
                        }
                        if (alert.equals(null)) {
                            call.respond("Alert not found")
                        } else {
                            call.respond(alert)
                        }
                    } catch (e: Exception) {
                        logger.info("Cannot fetch alert: $e")
                    }
                }

                put("/update-alert/{id}") {
                    val id = call.parameters["id"]?.toIntOrNull()
                    if (id == null) {
                        call.respond("Invalid alert ID")
                        return@put
                    }
                    try {
                        val alert = call.receive<LocationAlert>()
                        supabase.from("location_alerts").update(alert)
                        call.respond("Alert updated successfully")
                    } catch (e: Exception) {
                        logger.info("Cannot update alert: $e")
                    }
                }

                delete("/delete-alert/{id}") {
                    val id = call.parameters["id"]?.toIntOrNull()
                    if (id == null) {
                        call.respond("Invalid alert ID")
                        return@delete
                    }

                    try {
                        supabase.from("location_alerts").delete {
                            filter {
                                LocationAlert::alert_id eq id
                            }
                        }
                        call.respond("Alert deleted successfully")
                    } catch (e: Exception) {
                        logger.info("Failed to delete alert: $e")
                    }
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