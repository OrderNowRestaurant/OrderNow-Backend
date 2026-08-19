package ordernow.backend.ordernow_backend.responses;

import java.time.LocalDateTime;

public record ErrorResponse(
    int errorCode,
    String message,
    LocalDateTime timestamp
) {}
