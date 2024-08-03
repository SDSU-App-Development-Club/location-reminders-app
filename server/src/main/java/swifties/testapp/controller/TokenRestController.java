package swifties.testapp.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/token")
@RestController
public class TokenRestController {
    // Dummy endpoint that triggers 403 errors for invalid JWTs
    @GetMapping("/verify")
    public ResponseEntity<String> verify() {
        return ResponseEntity.ok("");
    }
}
