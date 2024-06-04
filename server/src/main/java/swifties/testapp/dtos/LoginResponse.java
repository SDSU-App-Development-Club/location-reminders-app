package swifties.testapp.dtos;

import swifties.testapp.entity.User;

public record LoginResponse(User user, String jwt) {
}
