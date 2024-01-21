package org.loopdfs.accountcardservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@Schema(
        name = "ErrorResponseDto",
        description = "Data Transfer Object (DTO) for error response details"
)
public class ErrorResponseDto {

    @Schema(
            description = "API Path where the error occurred",
            example = "/api/accounts"
    )
    private String apiPath;

    @Schema(
            description = "HTTP Status Code representing the error",
            example = "BAD_REQUEST"
    )
    private HttpStatus errorCode;

    @Schema(
            description = "Error message providing additional details about the error",
            example = "Required request parameter 'clientId' is not present"
    )
    private String errorMessage;

    @Schema(
            description = "Timestamp indicating when the error occurred",
            example = "2024-01-21T14:30:00"
    )
    private LocalDateTime timestamp;

    public ErrorResponseDto(String apiPath, HttpStatus errorCode, String errorMessage) {
        this.apiPath = apiPath;
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.timestamp = LocalDateTime.now();
    }
}
