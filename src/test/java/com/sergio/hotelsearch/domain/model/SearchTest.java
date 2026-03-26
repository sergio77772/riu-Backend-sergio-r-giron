package com.sergio.hotelsearch.domain.model;

import com.sergio.hotelsearch.domain.exception.DomainValidationException;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SearchTest {

    private static final LocalDate CHECK_IN = LocalDate.of(2025, 1, 10);
    private static final LocalDate CHECK_OUT = LocalDate.of(2025, 1, 15);

    @Test
    void shouldCreateSearchSuccessfully() {

        Search search = new Search("1", "hotel1", CHECK_IN, CHECK_OUT, List.of(30, 25));

        assertAll(
                () -> assertEquals("hotel1", search.hotelId()),
                () -> assertEquals(CHECK_IN, search.checkIn()),
                () -> assertEquals(CHECK_OUT, search.checkOut()),
                () -> assertEquals(List.of(30, 25), search.ages()));
    }

    @Test
    void shouldThrowExceptionIfCheckInAfterCheckOut() {

        assertThrows(DomainValidationException.class, () -> new Search("1", "hotel1",
                LocalDate.of(2025, 1, 20),
                LocalDate.of(2025, 1, 10),
                List.of(30)));
    }

    @Test
    void shouldThrowExceptionIfHotelIdIsNull() {
        assertThrows(DomainValidationException.class, () -> new Search("1", null, CHECK_IN, CHECK_OUT, List.of(30)));
    }

    @Test
    void shouldThrowExceptionIfHotelIdIsBlank() {

        assertThrows(DomainValidationException.class, () -> new Search("1", "   ", CHECK_IN, CHECK_OUT, List.of(30)));
    }
}
