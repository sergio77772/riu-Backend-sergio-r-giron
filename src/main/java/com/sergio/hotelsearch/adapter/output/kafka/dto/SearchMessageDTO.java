package com.sergio.hotelsearch.adapter.output.kafka.dto;

import java.time.LocalDate;
import java.util.List;

public record SearchMessageDTO(
        String searchId,
        String hotelId,
        LocalDate checkIn,
        LocalDate checkOut,
        List<Integer> ages) {
}
