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

                }

                put("/{id}") {
                    val id = call.parameters["id"]?.toIntOrNull()
                    if (id == null) {
                        call.respond("Invalid alert ID")
                        return@put
                    }
                    val alert = call.receive<LocationAlert>()

                    call.respond("Alert updated successfully")
                }

                delete("/{id}") {
                    val id = call.parameters["id"]?.toIntOrNull()
                    if (id == null) {
                        call.respond("Invalid alert ID")
                        return@delete
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