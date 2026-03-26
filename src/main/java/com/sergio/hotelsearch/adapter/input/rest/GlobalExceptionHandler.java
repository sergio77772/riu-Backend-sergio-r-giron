package com.sergio.hotelsearch.adapter.input.rest;

import com.sergio.hotelsearch.domain.exception.DomainValidationException;
import com.sergio.hotelsearch.domain.exception.SearchNotFoundException;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

/**
 * Global exception handler for REST layer.
 * Translates domain and application exceptions into proper HTTP responses.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles business rule violations (e.g. checkIn after checkOut, blank
     * hotelId).
     */
    @ExceptionHandler(DomainValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleDomainValidation(DomainValidationException ex) {
        return Map.of("error", ex.getMessage());
    }

    /**
     * Handles search not found errors.
     */
    @ExceptionHandler(SearchNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> handleSearchNotFound(SearchNotFoundException ex) {
        return Map.of("error", ex.getMessage());
    }

    /**
     * Handles @Valid annotation failures (e.g. @NotBlank, @NotEmpty, @NotNull).
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleValidation(MethodArgumentNotValidException ex) {
        FieldError fieldError = ex.getBindingResult().getFieldErrors().get(0);
        return Map.of("error", "%s: %s".formatted(fieldError.getField(), fieldError.getDefaultMessage()));
    }
}
