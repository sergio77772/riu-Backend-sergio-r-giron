package com.sergio.hotelsearch.adapter.output.persistence.adapter;

import com.sergio.hotelsearch.adapter.output.persistence.entity.SearchEntity;
import com.sergio.hotelsearch.adapter.output.persistence.repository.JpaSearchRepository;
import com.sergio.hotelsearch.domain.model.Search;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SearchRepositoryAdapterTest {

    private static final LocalDate CHECK_IN = LocalDate.of(2025, 1, 10);
    private static final LocalDate CHECK_OUT = LocalDate.of(2025, 1, 12);

    @Mock
    private JpaSearchRepository repository;

    @InjectMocks
    private SearchRepositoryAdapter adapter;
    private Search search;
    private SearchEntity entity;

    @BeforeEach
    void setUp() {
        search = new Search("1", "hotel1", CHECK_IN, CHECK_OUT, List.of(30));
        entity = new SearchEntity("1", "hotel1", CHECK_IN, CHECK_OUT, "30");
    }

    @Test
    void shouldSaveSearch() {
        adapter.save(search);
        verify(repository).save(any());
    }

    @Test
    void shouldCountSearch() {
        when(repository.countByHotelIdAndCheckInAndCheckOutAndAges(any(), any(), any(), any()))
                .thenReturn(5L);

        long count = adapter.countBySearch(search);

        assertEquals(5L, count);
    }

    @Test
    void shouldFindBySearchId() {
        when(repository.findBySearchId("1")).thenReturn(Optional.of(entity));

        Optional<Search> result = adapter.findBySearchId("1");

        assertAll(
                () -> assertTrue(result.isPresent()),
                () -> assertEquals("hotel1", result.get().hotelId()),
                () -> assertEquals(List.of(30), result.get().ages()),
                () -> assertEquals(CHECK_IN, result.get().checkIn()));
    }

    @Test
    void shouldReturnEmptyWhenSearchNotFound() {
        when(repository.findBySearchId("999")).thenReturn(Optional.empty());

        Optional<Search> result = adapter.findBySearchId("999");

        assertTrue(result.isEmpty());
    }
}
