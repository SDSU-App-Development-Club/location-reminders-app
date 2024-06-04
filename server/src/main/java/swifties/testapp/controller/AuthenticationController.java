package swifties.testapp.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import swifties.testapp.dtos.LoginResponse.UserResponse;
import swifties.testapp.dtos.LoginUserDto;
import swifties.testapp.dtos.RegisterUserDto;
import swifties.testapp.dtos.LoginResponse;
import swifties.testapp.entity.User;
import swifties.testapp.services.AuthenticationService;
import swifties.testapp.services.JwtService;

@RequestMapping("/auth")
@RestController
public class AuthenticationController {
    private final JwtService jwtService;

    private final AuthenticationService authenticationService;

    public AuthenticationController(JwtService jwtService, AuthenticationService authenticationService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
    }

    // Create account using email and password
    @PostMapping("/signup")
    public ResponseEntity<LoginResponse> register(@RequestBody RegisterUserDto registerUserDto) {
        return ResponseEntity.of(authenticationService.signup(registerUserDto).map(this::login));
    }

    // Login with email and password
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginUserDto loginUserDto) {
        return ResponseEntity.of(authenticationService.authenticate(loginUserDto).map(this::login));
    }

    // Return JWT with account details alongside it
    private LoginResponse login(User user) {
        return new LoginResponse(new UserResponse(user.getUserId(), user.getEmail()), jwtService.generateToken(user));
    }
}
