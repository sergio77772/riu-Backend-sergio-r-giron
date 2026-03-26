package com.sergio.hotelsearch.adapter.input.rest;

import com.sergio.hotelsearch.domain.exception.DomainValidationException;
import com.sergio.hotelsearch.domain.exception.SearchNotFoundException;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler exceptionHandler = new GlobalExceptionHandler();

    @Test
    void shouldHandleDomainValidationException() {
        DomainValidationException ex = new DomainValidationException("checkIn must be before checkOut");

        Map<String, String> response = exceptionHandler.handleDomainValidation(ex);

        assertAll(
                () -> assertEquals(1, response.size()),
                () -> assertEquals("checkIn must be before checkOut", response.get("error")));
    }

    @Test
    void shouldHandleSearchNotFoundException() {
        SearchNotFoundException ex = new SearchNotFoundException("999");

        Map<String, String> response = exceptionHandler.handleSearchNotFound(ex);

        assertAll(
                () -> assertEquals(1, response.size()),
                () -> assertEquals("Search not found for id=999", response.get("error")));
    }
}
