package swifties.testapp.responses;

public record LoginResponse(String token, long expiresIn) {
}