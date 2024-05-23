package swifties.testapp;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import swifties.testapp.entity.Alert;

import java.io.IOException;

@JsonTest
public class JsonTests {
    @Autowired
    private JacksonTester<Alert> json;

    @Test
    public void testDeserialize() {
        String obj = """
                {
                  "location_name": "henlo",
                  "latitude": 12.023351,
                  "longitude": 0.32421,
                  "radius": 300,
                  "message": "hi"
                }
                """;
        try {
            System.out.println(json.parse(obj).getObject());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
