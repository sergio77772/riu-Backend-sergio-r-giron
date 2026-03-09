package com.sergio.hotelsearch.adapter.input.rest.dto;

import com.sergio.hotelsearch.domain.model.Search;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

public record SearchRequestDTO(

                @NotBlank @Schema(example = "hotel-1234", description = "ID del Hotel") String hotelId,

                @NotBlank @Schema(example = "25/12/2026", description = "Formato: dd/MM/yyyy") String checkIn,

                @NotBlank @Schema(example = "31/12/2026", description = "Formato: dd/MM/yyyy") String checkOut,

                @NotEmpty @Schema(example = "[30, 28, 12]", description = "Edades de los huéspedes") List<Integer> ages) {

        private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        public Search toDomain() {

                return new Search(
                                UUID.randomUUID().toString(),
                                hotelId,
                                LocalDate.parse(checkIn, FORMATTER),
                                LocalDate.parse(checkOut, FORMATTER),
                                List.copyOf(ages));
        }
}
