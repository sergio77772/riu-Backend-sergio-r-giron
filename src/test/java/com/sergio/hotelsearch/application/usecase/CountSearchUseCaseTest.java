package com.sergio.hotelsearch.application.usecase;

import com.sergio.hotelsearch.domain.model.Search;
import com.sergio.hotelsearch.domain.port.SearchRepositoryPort;
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
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CountSearchUseCaseTest {

    @Mock
    private SearchRepositoryPort repository;

    @InjectMocks
    private CountSearchUseCase useCase;

    private Search search;

    @BeforeEach
    void setUp() {
        search = new Search(
                "1",
                "hotel1",
                LocalDate.of(2025, 1, 10),
                LocalDate.of(2025, 1, 12),
                List.of(30)
        );
    }

    @Test
    void shouldReturnSearchCount() {

        when(repository.findBySearchId("1")).thenReturn(Optional.of(search));
        when(repository.countBySearch(search)).thenReturn(5L);
        CountSearchUseCase.Result result = useCase.execute("1");
        assertAll(
                () -> assertEquals(5L, result.count()),
                () -> assertEquals("hotel1", result.search().hotelId()),
                () -> assertEquals("1", result.search().searchId())
        );
    }
    @Test
    void shouldThrowExceptionWhenSearchNotFound() {
        when(repository.findBySearchId("999")).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class,
                () -> useCase.execute("999"));
    }
}
