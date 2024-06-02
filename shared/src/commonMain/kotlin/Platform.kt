import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.Serializable

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform

object RestAPIAccess {
    private val httpClient = HttpClient {
        install(ContentNegotiation) {
            json()
        }
    }

    suspend fun attemptSignup(username: String, password: String) {
        @Serializable
        data class SignupDto(val username: String, val password: String)

        // TODO: Fix the Spring backend to use TLS
        httpClient.post("http://10.0.2.2:8080/auth/signup") {
            contentType(ContentType.Application.Json)
            setBody(SignupDto(username, password))
        }
    }
}