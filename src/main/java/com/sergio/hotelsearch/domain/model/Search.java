package com.sergio.hotelsearch.domain.model;

import com.sergio.hotelsearch.domain.exception.DomainValidationException;

import java.time.LocalDate;
import java.util.List;

/**
 * Represents a hotel availability search request.
 * Immutable domain entity enforcing business invariants at construction time.
 */
public record Search(
        String searchId,
        String hotelId,
        LocalDate checkIn,
        LocalDate checkOut,
        List<Integer> ages) {

    public Search {

        if (hotelId == null || hotelId.isBlank()) {
            throw new DomainValidationException("hotelId cannot be empty");
        }

        if (checkIn.isAfter(checkOut)) {
            throw new DomainValidationException("checkIn must be before checkOut");
        }

        ages = List.copyOf(ages);
    }
}
