package swifties.testapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import swifties.testapp.entity.Alert;

@Repository
public interface AlertRepository extends JpaRepository<Alert, Integer> {
}
