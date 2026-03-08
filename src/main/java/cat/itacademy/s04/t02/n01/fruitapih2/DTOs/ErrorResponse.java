package cat.itacademy.s04.t02.n01.fruitapih2.DTOs;

import java.time.LocalDateTime;
import java.util.Map;

public record ErrorResponse(
        LocalDateTime timestamp,
        int status,
        String message,
        Map<String, String> errors
) {
    public ErrorResponse(int status, String message) {
        this(LocalDateTime.now(), status, message, null);
    }

    public ErrorResponse(int status, String message, Map<String, String> errors) {
        this(LocalDateTime.now(), status, message, errors);
    }
}
