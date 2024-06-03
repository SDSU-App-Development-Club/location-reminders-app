package swifties.testapp.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import swifties.testapp.dtos.LoginUserDto;
import swifties.testapp.dtos.RegisterUserDto;
import swifties.testapp.entity.User;
import swifties.testapp.responses.LoginResponse;
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

    @PostMapping("/signup")
    public ResponseEntity<User> register(@RequestBody RegisterUserDto registerUserDto) {
        return ResponseEntity.of(authenticationService.signup(registerUserDto));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginUserDto loginUserDto) {
        return ResponseEntity.of(authenticationService.authenticate(loginUserDto).map(user -> {
            String jwtToken = jwtService.generateToken(user);
            return new LoginResponse(jwtToken, jwtService.getExpirationTime());
        }));
    }
}
