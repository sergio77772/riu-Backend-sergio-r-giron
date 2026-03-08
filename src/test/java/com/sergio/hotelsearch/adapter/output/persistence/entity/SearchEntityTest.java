package com.sergio.hotelsearch.adapter.output.persistence.entity;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SearchEntityTest {

    @Test
    void shouldCreateEntity() {

        SearchEntity entity = new SearchEntity(
                "1",
                "hotel1",
                LocalDate.now(),
                LocalDate.now().plusDays(1),
                "30"
        );

        assertEquals("hotel1", entity.getHotelId());
    }
}