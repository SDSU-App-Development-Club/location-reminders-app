package swifties.testapp.controller;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import swifties.testapp.dtos.PlaceDto;

import java.util.Map;

// Proxy endpoint for certain Google APIs, hides our Google token. No data is cached on the server except for Place IDs.
@RestController()
@RequestMapping("/maps")
public class MapsController {
    @Value("${google.maps.api.key}")
    private String apiKey;

    private final RestTemplate template;

    public MapsController(RestTemplate template) {
        this.template = template;
    }

    @GetMapping("/autocomplete")
    public ResponseEntity<PlaceDto> fetchPlacePredictions(@RequestParam String query) {
        String url = "https://maps.googleapis.com/maps/api/place/autocomplete/json";

        LoggerFactory.getLogger("main").error(this.template.getForObject(url, String.class, Map.of("input", query, "key", apiKey)));

        return ResponseEntity.notFound().build();
    }
}
