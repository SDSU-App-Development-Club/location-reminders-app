import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.Serializable

@Serializable
data class UserResponse(val userId: Int, val email: String)
@Serializable
data class LoginResponse(val user: UserResponse, val jwt: String)
@Serializable
data class SignupDto(val email: String, val password: String)

expect val API_HOST: String

// TODO: Fix the Spring backend to use TLS
object RestAPIAccess {
    private val httpClient = HttpClient {
        install(ContentNegotiation) {
            json()
        }
    }

    suspend fun attemptSignup(username: String, password: String): LoginResponse? {
        val response: HttpResponse = httpClient.post("$API_HOST/auth/signup") {
            contentType(ContentType.Application.Json)
            setBody(SignupDto(username, password))
        }
        if (response.status == HttpStatusCode.Conflict) {
            return null
        }
        return response.body()
    }

    suspend fun attemptLogin(username: String, password: String): LoginResponse? {
        val response: HttpResponse = httpClient.post("$API_HOST/auth/login") {
            contentType(ContentType.Application.Json)
            setBody(SignupDto(username, password))
        }

        if (response.status == HttpStatusCode.Forbidden) {
            return null
        }

        return response.body()
    }
}