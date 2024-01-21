package org.loopdfs.accountcardservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@AllArgsConstructor
public class UnprocessableEntityErrorDto {

    private String apiPath;
    private HttpStatus errorCode;
    private LocalDateTime timestamp;
    private Map<String, String> errors;

    public UnprocessableEntityErrorDto(String apiPath, HttpStatus errorCode, Map<String, String> errors) {
        this.apiPath = apiPath;
        this.errorCode = errorCode;
        this.timestamp = LocalDateTime.now();
        this.errors = errors;
    }
}
