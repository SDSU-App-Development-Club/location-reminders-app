package swifties.testapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import swifties.testapp.entity.Alert;

import java.util.List;

@Repository
public interface AlertRepository extends JpaRepository<Alert, Integer> {
    @Query("SELECT a FROM Alert a WHERE a.userId = :userId")
    List<Alert> getAlertsForUser(int userId);
}