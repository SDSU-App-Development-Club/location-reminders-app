import kotlin.random.Random

class Greeting {
    private val platform = getPlatform()

    fun greet(): String {
        // Use Kotlin random instead of Java (Kotlin is available on iOS, Java is not)
        val firstWord = if (Random.nextBoolean()) "Hi!" else "Hello"
        return "$firstWord Guess what this is! > ${platform.name.reversed()}!"
    }
}