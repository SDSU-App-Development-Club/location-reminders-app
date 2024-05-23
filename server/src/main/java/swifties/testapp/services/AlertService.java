package swifties.testapp.services;

import org.springframework.stereotype.Service;
import swifties.testapp.entity.Alert;
import swifties.testapp.repository.AlertRepository;

@Service
public class AlertService {
    private final AlertRepository repository;

    public AlertService(AlertRepository repository) {
        this.repository = repository;
    }

    public Alert saveAlert(Alert alert) {
        return repository.save(alert);
    }
}
