package swifties.testapp

import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.ktor.server.application.ApplicationEnvironment


val supabase = createSupabaseClient(
    supabaseUrl = "https://yavstzicdcjbqsvnfhxy.supabase.co",
    supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InlhdnN0emljZGNqYnFzdm5maHh5Iiwicm9sZSI6ImFub24iLCJpYXQiOjE3MTU0OTI3NDAsImV4cCI6MjAzMTA2ODc0MH0.MK6D-tFU7TRV2sLKFSnLNdKKnoaoJvquzFXanDG5KB4"
) {
    install(Postgrest)
}

