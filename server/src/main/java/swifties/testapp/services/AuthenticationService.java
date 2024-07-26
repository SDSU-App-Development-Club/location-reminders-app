package swifties.testapp.services;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

import swifties.testapp.dtos.LoginUserDto;
import swifties.testapp.dtos.RegisterUserDto;
import swifties.testapp.entity.Result;
import swifties.testapp.entity.User;
import swifties.testapp.repository.UserRepository;

@Service
public class AuthenticationService {
    // requires valid LOWERCASE emails
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
    // At least 8 characters long, including at least 1 lowercase character, 1 uppercase character, 1 numeric character, 1 non-alphanumeric character
    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^A-Za-z0-9]).{8,}$");

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthenticationService(UserRepository userRepository, AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Result<User, HttpStatus> register(RegisterUserDto input) {
        // todo: enforce lowercase emails from the frontend code
        String email = input.getEmail();

        if (this.userRepository.existsByEmail(email)) {
            return Result.error(HttpStatus.CONFLICT);
        } else {
            if (EMAIL_PATTERN.matcher(email).matches()) {
                User user = new User()
                        .setEmail(email)
                        .setPasswordHash(this.passwordEncoder.encode(input.getPassword()));

                return Result.ok(this.userRepository.save(user));
            } else {
                return Result.error(HttpStatus.BAD_REQUEST);
            }
        }
    }

    public Result<User, HttpStatus> login(LoginUserDto input) {
        String email = input.getEmail();

        if (EMAIL_PATTERN.matcher(email).matches() && PASSWORD_PATTERN.matcher(input.getPassword()).matches()) {
            try {
                this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, input.getPassword()));
            } catch (AuthenticationException exception) {
                return Result.error(HttpStatus.UNAUTHORIZED);
            }

            return Result.maybe(this.userRepository.findByEmail(email), HttpStatus.NOT_FOUND);
        } else {
            return Result.error(HttpStatus.BAD_REQUEST);
        }
    }
}

