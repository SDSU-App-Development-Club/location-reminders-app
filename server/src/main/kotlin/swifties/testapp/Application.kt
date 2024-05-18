package swifties.testapp

import Greeting
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.ktor.http.ContentType.Application.Json
import io.ktor.http.HttpStatusCode
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.plugins.defaultheaders.DefaultHeaders
import io.ktor.server.plugins.statuspages.StatusPages
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.json.Json

fun main(args: Array<String>) = EngineMain.main(args)
fun Application.module() {
    install(DefaultHeaders)

    install(ContentNegotiation) {
        json(Json {
            prettyPrint = true
            isLenient = true
        })
    }

    install(StatusPages) {
        exception<Throwable> { call, cause ->
            call.respondText(text = "500: $cause", status = HttpStatusCode.InternalServerError)
        }
    }
//    val taskDto = TaskDao()
//
//    install(Routing) {
//        task(taskDao)
//    }

    try {
        val supabase = createSupabaseClient(
            supabaseUrl = "https://yavstzicdcjbqsvnfhxy.supabase.co",
            supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InlhdnN0emljZGNqYnFzdm5maHh5Iiwicm9sZSI6ImFub24iLCJpYXQiOjE3MTU0OTI3NDAsImV4cCI6MjAzMTA2ODc0MH0.MK6D-tFU7TRV2sLKFSnLNdKKnoaoJvquzFXanDG5KB4"
        ) {
            install(Postgrest)
        }
    } catch (e: Exception) {
        print("Cannot connect to Supabase: ${e}")
    }


}