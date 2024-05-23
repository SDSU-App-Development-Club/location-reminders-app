package swifties.testapp.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import swifties.testapp.entity.Alert;
import swifties.testapp.services.AlertService;

@RestController
@RequestMapping("/alerts")
public class AlertRestController {
    private final AlertService repository;

    public AlertRestController(AlertService repository) {
        this.repository = repository;
    }

    @PostMapping(value = "/create")
    public ResponseEntity<?> createAlert(@RequestBody Alert alert) {
        return new ResponseEntity<>(repository.saveAlert(alert), HttpStatus.CREATED);
    }
}
