package com.sergio.hotelsearch.domain.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SearchTest {

    @Test
    void shouldCreateSearchSuccessfully() {

        Search search = new Search(
                "1",
                "hotel1",
                LocalDate.of(2025,1,10),
                LocalDate.of(2025,1,15),
                List.of(30,25)
        );

        assertEquals("hotel1", search.hotelId());
    }

    @Test
    void shouldThrowExceptionIfCheckInAfterCheckOut() {

        assertThrows(IllegalArgumentException.class, () ->
                new Search(
                        "1",
                        "hotel1",
                        LocalDate.of(2025,1,20),
                        LocalDate.of(2025,1,10),
                        List.of(30)
                )
        );
    }
}