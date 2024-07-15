package swifties.testapp.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import swifties.testapp.dtos.LoginResponse.UserResponse;
import swifties.testapp.dtos.LoginUserDto;
import swifties.testapp.dtos.RegisterUserDto;
import swifties.testapp.dtos.LoginResponse;
import swifties.testapp.entity.Result;
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

    // Create account using email and password. Returns 409 if the account wasn't found
    @PostMapping("/signup")
    public ResponseEntity<LoginResponse> signup(@RequestBody RegisterUserDto registerUserDto) {
        return handleLoginResult(this.authenticationService.register(registerUserDto));
    }

    // Login with email and password
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginUserDto loginUserDto) {
        return handleLoginResult(this.authenticationService.login(loginUserDto));
    }

    // Return JWT with account details alongside it
    private ResponseEntity<LoginResponse> handleLoginResult(Result<User, HttpStatus> result) {
        return result
                .map(user -> new LoginResponse(new UserResponse(user.getUserId(), user.getEmail()), this.jwtService.generateToken(user)))
                .map(ResponseEntity::ok)
                .orElse(status -> ResponseEntity.status(status).build());
    }
}
