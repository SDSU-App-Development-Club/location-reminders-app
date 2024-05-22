package swifties.testapp

import io.github.cdimascio.dotenv.Dotenv
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.from
import io.ktor.serialization.jackson.jackson
import io.ktor.server.application.call
import io.ktor.server.application.install
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.delete
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.put
import io.ktor.server.routing.route
import io.ktor.server.routing.routing
import org.slf4j.Logger
import org.slf4j.LoggerFactory

val logger: Logger = LoggerFactory.getLogger("intro-app")
val env: Dotenv = Dotenv.load()

fun main() {
    embeddedServer(Netty, port = 8080) {
        install(ContentNegotiation) {
            jackson()
        }

        val supabase: SupabaseClient

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
                        val alert = supabase.from("location_alerts").select {
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
