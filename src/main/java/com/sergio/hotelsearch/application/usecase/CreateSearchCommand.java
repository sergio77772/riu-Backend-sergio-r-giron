package com.sergio.hotelsearch.application.usecase;

import java.time.LocalDate;
import java.util.List;

/**
 * Input model for the CreateSearch use case.
 * Carries search data without an identity — the use case assigns the ID.
 */
public record CreateSearchCommand(
        String hotelId,
        LocalDate checkIn,
        LocalDate checkOut,
        List<Integer> ages) {
}
