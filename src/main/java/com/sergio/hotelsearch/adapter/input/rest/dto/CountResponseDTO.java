package com.sergio.hotelsearch.adapter.input.rest.dto;

import com.sergio.hotelsearch.domain.model.Search;

import java.time.format.DateTimeFormatter;
import java.util.List;

public record CountResponseDTO(
        String searchId,
        SearchDetailDTO search,
        long count
) {

    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static CountResponseDTO from(String searchId, Search search, long count) {
        return new CountResponseDTO(
                searchId,
                new SearchDetailDTO(
                        search.hotelId(),
                        search.checkIn().format(FORMATTER),
                        search.checkOut().format(FORMATTER),
                        search.ages()
                ),
                count
        );
    }

    public record SearchDetailDTO(
            String hotelId,
            String checkIn,
            String checkOut,
            List<Integer> ages
    ) {}
}
