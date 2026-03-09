package com.sergio.hotelsearch.adapter.output.persistence.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class SearchEntityTest {

    private static final LocalDate CHECK_IN = LocalDate.of(2025, 1, 10);
    private static final LocalDate CHECK_OUT = LocalDate.of(2025, 1, 12);

    private SearchEntity entity;

    @BeforeEach
    void setUp() {
        entity = new SearchEntity("1", "hotel1", CHECK_IN, CHECK_OUT, "30,25");
    }

    @Test
    void shouldCreateEntityWithAllFields() {
        assertAll(
                () -> assertEquals("1", entity.getSearchId()),
                () -> assertEquals("hotel1", entity.getHotelId()),
                () -> assertEquals(CHECK_IN, entity.getCheckIn()),
                () -> assertEquals(CHECK_OUT, entity.getCheckOut()),
                () -> assertEquals("30,25", entity.getAges()));
    }
}
