package swifties.testapp.dtos;


public record LoginResponse(UserResponse user, String jwt) {
    public record UserResponse(
            int userId,
            String email
    ) {
    }
}
