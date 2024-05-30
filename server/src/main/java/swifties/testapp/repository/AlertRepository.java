package swifties.testapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import swifties.testapp.dtos.LoginUserDto;
import swifties.testapp.dtos.RegisterUserDto;
import swifties.testapp.entity.Alert;
import swifties.testapp.entity.User;

@Repository
public interface AlertRepository extends JpaRepository<Alert, Integer> {
}