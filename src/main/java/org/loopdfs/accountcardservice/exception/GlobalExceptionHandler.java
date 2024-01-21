package org.loopdfs.accountcardservice.exception;

import org.loopdfs.accountcardservice.dto.ErrorResponseDto;
import org.loopdfs.accountcardservice.dto.UnprocessableEntityErrorDto;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.web.client.HttpServerErrorException.InternalServerError;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {


    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        Map<String, String> validationErrors = ex.getBindingResult().getAllErrors().stream()
                .map(error -> (FieldError) error)
                .collect(Collectors.toMap(FieldError::getField, fieldError -> Optional.ofNullable(fieldError.getDefaultMessage()).orElse("invalid data")));


        UnprocessableEntityErrorDto entityErrorDto = new UnprocessableEntityErrorDto(
                request.getDescription(false),
                HttpStatus.UNPROCESSABLE_ENTITY,
                validationErrors
        );

        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(entityErrorDto);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Object> handleResourceNotFoundException(
            ResourceNotFoundException resourceNotFoundException,
            WebRequest webRequest
    ) {
        return handleException(resourceNotFoundException, webRequest, HttpStatus.NOT_FOUND);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex,
                                                             Object body,
                                                             HttpHeaders headers,
                                                             HttpStatusCode statusCode,
                                                             WebRequest request) {
        HttpStatus status = ex instanceof InternalServerError ? HttpStatus.INTERNAL_SERVER_ERROR : HttpStatus.BAD_REQUEST;
        return handleException(ex, request, status);
    }

    private ResponseEntity<Object> handleException(
            Exception exception,
            WebRequest webRequest,
            HttpStatus httpStatus
    ) {
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(
                webRequest.getDescription(false),
                httpStatus,
                exception.getMessage()
        );
        return ResponseEntity.status(httpStatus).body(errorResponseDto);
    }
}
