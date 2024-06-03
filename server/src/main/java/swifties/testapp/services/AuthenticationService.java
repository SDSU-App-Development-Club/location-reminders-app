package swifties.testapp.services;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import swifties.testapp.dtos.LoginUserDto;
import swifties.testapp.dtos.RegisterUserDto;
import swifties.testapp.entity.User;
import swifties.testapp.repository.UserRepository;

import java.util.Optional;

@Service
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthenticationService(UserRepository userRepository, AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Optional<User> signup(RegisterUserDto input) {
        if (userRepository.existsByEmail(input.getEmail())) {
            return Optional.empty();
        } else {
            User user = new User()
                    .setEmail(input.getEmail())
                    .setPasswordHash(passwordEncoder.encode(input.getPassword()));

            return Optional.of(userRepository.save(user));
        }
    }

    public Optional<User> authenticate(LoginUserDto input) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(input.getEmail(), input.getPassword()));

        return userRepository.findByEmail(input.getEmail());
    }
}

