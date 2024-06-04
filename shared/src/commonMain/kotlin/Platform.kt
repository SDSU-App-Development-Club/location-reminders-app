import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.Serializable

@Serializable( )
data class UserResponse(val userId: Int, val email: String)
@Serializable
data class LoginResponse(val user: UserResponse, val jwt: String)
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

    suspend fun attemptSignup(username: String, password: String): LoginResponse {
        @Serializable
        data class SignupDto(val email: String, val password: String)

        // TODO: Fix the Spring backend to use TLS
        val response: HttpResponse = httpClient.post("http://10.0.2.2:8080/auth/signup") {
            contentType(ContentType.Application.Json)
            setBody(SignupDto(username, password))
        }
        return response.body()
    }
}