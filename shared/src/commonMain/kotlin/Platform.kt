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

    suspend fun attemptSignup(username: String, password: String): Result<LoginResponse, HttpStatusCode> {
        val response: HttpResponse = httpClient.post("$API_HOST/auth/signup") {
            contentType(ContentType.Application.Json)
            setBody(SignupDto(username, password))
        }
        return if (response.status == HttpStatusCode.OK) {
            Result.ok(response.body())
        } else {
            Result.error(response.status)
        }
    }

    suspend fun attemptLogin(username: String, password: String): Result<LoginResponse, HttpStatusCode> {
        val response: HttpResponse = httpClient.post("$API_HOST/auth/login") {
            contentType(ContentType.Application.Json)
            setBody(SignupDto(username, password))
        }
        return if (response.status == HttpStatusCode.OK) {
            Result.ok(response.body())
        } else {
            Result.error(response.status)
        }
    }
}

// Copied from backend
// For returning value or error to the platform-specific frontend easily
// To use, check result.isOk() before accessing values
class Result<V, E> private constructor(val ok: Boolean, private val value: V?, private val error: E?) {
    fun value(): V {
        return this.value ?: throw NullPointerException("This result is an error.")
    }

    fun error(): E {
        return this.error ?: throw NullPointerException("This result is not an error.")
    }

    fun <T> map(mapper: (V) -> T): Result<T, out E> {
        return if (this.ok) {
            Result(true, mapper(this.value!!), null)
        } else {
            Result(false, null, this.error!!)
        }
    }

    fun orElse(defaultValue: (E) -> V): V {
        return this.value ?: defaultValue(this.error!!)
    }

    companion object {
        fun <V, E> ok(value: V): Result<V, E> {
            return Result(true, value, null)
        }

        fun <V, E> error(error: E): Result<V, E> {
            return Result(true, null, error)
        }

        fun <V, E> maybe(value: V?, error: E): Result<V, E> {
            return if (value != null) {
                ok(value)
            } else {
                error(error)
            }
        }
    }
}
