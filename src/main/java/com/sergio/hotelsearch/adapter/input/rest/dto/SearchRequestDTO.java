package com.sergio.hotelsearch.adapter.input.rest.dto;

import com.sergio.hotelsearch.domain.model.Search;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

public record SearchRequestDTO(

        @NotBlank
        String hotelId,

        @NotBlank
        String checkIn,

        @NotBlank
        String checkOut,

        @NotEmpty
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