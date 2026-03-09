package com.sergio.hotelsearch.adapter.input.rest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.format.DateTimeParseException;
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
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleIllegalArgument(IllegalArgumentException ex) {
        return Map.of("error", ex.getMessage());
    }

    /**
     * Handles invalid date format (expected dd/MM/yyyy).
     */
    @ExceptionHandler(DateTimeParseException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleDateTimeParse(DateTimeParseException ex) {
        return Map.of("error", "Invalid date format. Expected: dd/MM/yyyy. Received: " + ex.getParsedString());
    }
}
