package swifties.testapp.services;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import swifties.testapp.entity.Alert;
import swifties.testapp.repository.AlertRepository;

import java.util.List;
import java.util.Optional;

@Service
public class AlertService {
    private final AlertRepository repository;

    public AlertService(AlertRepository repository) {
        this.repository = repository;
    }

    public Alert saveAlert(Alert alert) {
        return this.repository.save(alert);
    }

    public List<Alert> getAlertsForUser(int userId) {
        return this.repository.getAlertsForUser(userId);
    }

    public ResponseEntity<?> deleteAlert(int userId, int alertId) {
        Optional<Alert> alert = this.repository.findById(alertId);
        if (alert.isPresent() && alert.get().getUserId() == userId) {
            this.repository.deleteById(alertId);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
