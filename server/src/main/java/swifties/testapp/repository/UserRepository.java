package swifties.testapp.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import jakarta.annotation.Nullable;
import swifties.testapp.entity.User;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
    @Nullable
    User findByEmail(String email);

    boolean existsByEmail(String email);
}