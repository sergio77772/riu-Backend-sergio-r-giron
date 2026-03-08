package com.sergio.hotelsearch.adapter.output.persistence.adapter;

import com.sergio.hotelsearch.adapter.output.persistence.repository.JpaSearchRepository;
import com.sergio.hotelsearch.domain.model.Search;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SearchRepositoryAdapterTest {

    @Mock
    private JpaSearchRepository repository;

    @InjectMocks
    private SearchRepositoryAdapter adapter;

    @Test
    void shouldSaveSearch() {

        Search search = new Search(
                "1",
                "hotel1",
                LocalDate.now(),
                LocalDate.now().plusDays(1),
                List.of(30)
        );

        adapter.save(search);

        verify(repository).save(any());
    }

    @Test
    void shouldCountSearch() {

        Search search = new Search(
                "1",
                "hotel1",
                LocalDate.now(),
                LocalDate.now().plusDays(1),
                List.of(30)
        );

        when(repository.countByHotelIdAndCheckInAndCheckOutAndAges(any(),any(),any(),any()))
                .thenReturn(5L);

        long count = adapter.countBySearch(search);

        assertEquals(5, count);
    }
}
