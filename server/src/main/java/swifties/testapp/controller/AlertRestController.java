package swifties.testapp.controller;

import jakarta.annotation.Nullable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import swifties.testapp.entity.Alert;
import swifties.testapp.entity.Result;
import swifties.testapp.entity.User;
import swifties.testapp.services.AlertService;

@RestController
@RequestMapping("/alerts")
public class AlertRestController {
    private final AlertService alertService;

    public AlertRestController(AlertService alertService) {
        this.alertService = alertService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createAlert(@AuthenticationPrincipal User user, @RequestBody CreateAlertDto dto) {
        Alert alert = new Alert();
        alert.setTitle(dto.title);
        alert.setEmoji(dto.emoji);
        alert.setMessage(dto.message);
        alert.setPlaceId(dto.placeId);
        alert.setUserId(user.getUserId());
        return new ResponseEntity<>(alertService.saveAlert(alert), HttpStatus.CREATED);
    }

    @GetMapping("/get")
    public ResponseEntity<?> getAlerts(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(this.alertService.getAlertsForUser(user.getUserId()));
    }

    @PostMapping("/delete")
    public ResponseEntity<?> deleteAlert(@AuthenticationPrincipal User user, int alertId) {
        return this.alertService.deleteAlert(user.getUserId(), alertId);
    }

    public record CreateAlertDto(String title, @Nullable String emoji, String message, String placeId) {}
}
