package swifties.testapp.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "alerts", schema = "public")
public class Alert {
    // ID assigned so that client may specify an alert to delete
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "alert_id")
    private int alertId;

    // User who created this task
    @Column(name = "user_id")
    private int userId;

    // name of task
    @Column(name = "title", nullable = false)
    private String title;

    // emoji icon of task
    @Column(name = "emoji", nullable = false)
    private String emoji;

    // note with a detailed description of task. should be limited to 560
    @Column(name = "message", nullable = false)
    private String message;

    // the unique identifier for the physical location of the task for use in Google Places API
    @Column(name = "place_id", nullable = false)
    private String placeId;
}
