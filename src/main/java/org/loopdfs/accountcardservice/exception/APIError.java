package org.loopdfs.accountcardservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class APIError extends RuntimeException {

    public APIError(String message) {
        super(message);
    }
}
