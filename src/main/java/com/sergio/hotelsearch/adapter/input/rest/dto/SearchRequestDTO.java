package com.sergio.hotelsearch.adapter.input.rest.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sergio.hotelsearch.application.usecase.CreateSearchCommand;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;

public record SearchRequestDTO(

                @NotBlank @Schema(example = "hotel-1234", description = "ID del Hotel") String hotelId,

                @NotNull @JsonFormat(pattern = "dd/MM/yyyy") @Schema(example = "25/12/2026", description = "Formato: dd/MM/yyyy") LocalDate checkIn,

                @NotNull @JsonFormat(pattern = "dd/MM/yyyy") @Schema(example = "31/12/2026", description = "Formato: dd/MM/yyyy") LocalDate checkOut,

                @NotEmpty @Schema(example = "[30, 28, 12]", description = "Edades de los huéspedes") List<Integer> ages) {

        public CreateSearchCommand toCommand() {
                return new CreateSearchCommand(hotelId, checkIn, checkOut, List.copyOf(ages));
        }
}
