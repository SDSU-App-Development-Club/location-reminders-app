package swifties.testapp.responses;

import lombok.Getter;
import lombok.Setter;


public class LoginResponse {
    private String token;

    private long expiresIn;

    public String getToken() {
        return token;
    }

    public LoginResponse setToken(String token) {
        this.token = token;
        return this;
    }

    public LoginResponse setExpiresIn(long expiration) {
        this.expiresIn = expiration;
        return this;
    }
    // Getters and setters...
}