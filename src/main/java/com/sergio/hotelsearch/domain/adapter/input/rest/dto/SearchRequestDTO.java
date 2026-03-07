package com.sergio.hotelsearch.domain.adapter.input.rest.dto;
import com.sergio.hotelsearc.domain.model.Search;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

public record SearchRequestDTO(
        String hotelId,
        String checkIn,
        String checkOut,
        List<Integer> ages
) {

    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public Search toDomain() {

        return new Search(
                UUID.randomUUID().toString(),
                hotelId,
                LocalDate.parse(checkIn, FORMATTER),
                LocalDate.parse(checkOut, FORMATTER),
                List.copyOf(ages)
        );
    }
}