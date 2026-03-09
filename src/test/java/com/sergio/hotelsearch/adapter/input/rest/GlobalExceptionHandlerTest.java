package com.sergio.hotelsearch.adapter.input.rest;

import org.junit.jupiter.api.Test;
import java.time.format.DateTimeParseException;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.assertEquals;

class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler exceptionHandler = new GlobalExceptionHandler();

    @Test
    void shouldHandleIllegalArgumentException() {
        IllegalArgumentException ex = new IllegalArgumentException("checkIn must be before checkOut");

        Map<String, String> response = exceptionHandler.handleIllegalArgument(ex);

        assertEquals("checkIn must be before checkOut", response.get("error"));
    }

    @Test
    void shouldHandleDateTimeParseException() {
        DateTimeParseException ex = new DateTimeParseException("Text '2025-01-10' could not be parsed at index 0",
                "2025-01-10", 0);

        Map<String, String> response = exceptionHandler.handleDateTimeParse(ex);

        assertEquals("Invalid date format. Expected: dd/MM/yyyy. Received: 2025-01-10", response.get("error"));
    }
}
