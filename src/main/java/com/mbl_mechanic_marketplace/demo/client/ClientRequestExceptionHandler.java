package com.mbl_mechanic_marketplace.demo.client;

import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Comparator;
import java.util.List;

/** Converts common client-request parsing and validation failures into readable HTTP 400 responses. */
@RestControllerAdvice
public class ClientRequestExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public RequestErrorResponse handleValidation(MethodArgumentNotValidException exception) {
        List<FieldError> errors = exception.getBindingResult()
                .getAllErrors()
                .stream()
                .map(error -> new FieldError(
                        error instanceof org.springframework.validation.FieldError fieldError
                                ? fieldError.getField()
                                : "request",
                        error.getDefaultMessage()
                ))
                .sorted(Comparator.comparing(FieldError::field).thenComparing(FieldError::message))
                .toList();

        return new RequestErrorResponse("The request contains invalid fields.", errors);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public RequestErrorResponse handleUnreadableRequest(HttpMessageNotReadableException exception) {
        return new RequestErrorResponse(
                "The request body could not be read. Check that it is valid JSON and that values use the expected formats (entityType must be PERSON or LEGAL_ENTITY).",
                List.of()
        );
    }

    /** JSON response format used for request errors. */
    public record RequestErrorResponse(String message, List<FieldError> errors) {
    }

    /** One invalid request property, or {@code request} for a class-level validation failure. */
    public record FieldError(String field, String message) {
    }
}
