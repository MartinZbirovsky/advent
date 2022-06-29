package advent.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
public class ApiException {
    private final String error;
    private final HttpStatus httpStatus;
    private final ZonedDateTime timestamp;
}

