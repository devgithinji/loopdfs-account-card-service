package org.loopdfs.accountcardservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@AllArgsConstructor
@Schema(
        name = "UnprocessableEntityErrorDto",
        description = "DTO for representing an unprocessable entity error response"
)
public class UnprocessableEntityErrorDto {

    @Schema(
            description = "API path that caused the error",
            example = "/api/some-endpoint"
    )
    private String apiPath;

    @Schema(
            description = "HTTP error code",
            example = "422"
    )
    private HttpStatus errorCode;

    @Schema(
            description = "Timestamp of the error",
            example = "2024-01-25T10:15:30"
    )
    private LocalDateTime timestamp;

    @Schema(
            description = "Map of field errors",
            example = "{\"fieldName\": \"Error message\"}"
    )
    private Map<String, String> errors;

    public UnprocessableEntityErrorDto(String apiPath, HttpStatus errorCode, Map<String, String> errors) {
        this.apiPath = apiPath;
        this.errorCode = errorCode;
        this.timestamp = LocalDateTime.now();
        this.errors = errors;
    }
}
