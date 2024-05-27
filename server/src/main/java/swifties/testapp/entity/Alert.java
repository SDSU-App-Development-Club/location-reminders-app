package swifties.testapp.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "alerts", schema = "public")
public class Alert {
    // Should we add JsonProperty???
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "alert_id")
    private int alertId;

    @JsonProperty(value = "location_name")
    @Column(name = "location_name", nullable = false)
    private String locationName;

    @Column(name = "latitude", nullable = false)
    private double latitude;

    @Column(name = "longitude", nullable = false)
    private double longitude;

    @Column(name = "radius", nullable = false)
    private int radius;

    @Column(name = "message", nullable = false)
    private String message;

    @Column(name = "active")
    private boolean active;

    @JsonProperty("created_at")
    @Column(name = "created_at")
    private Timestamp createdAt;
}
