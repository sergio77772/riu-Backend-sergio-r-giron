package com.sergio.hotelsearch.application.usecase;

import com.sergio.hotelsearch.domain.model.Search;
import com.sergio.hotelsearch.domain.port.SearchRepositoryPort;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class CountSearchUseCaseTest {

    @Mock
    private SearchRepositoryPort repository;

    @InjectMocks
    private CountSearchUseCase useCase;

    @Test
    void shouldReturnSearchCount() {

        Search search = new Search(
                "1",
                "hotel1",
                LocalDate.now(),
                LocalDate.now().plusDays(1),
                List.of(30)
        );

        when(repository.findBySearchId("1")).thenReturn(Optional.of(search));
        when(repository.countBySearch(search)).thenReturn(5L);

        long count = useCase.execute("1");

        assertEquals(5, count);
    }
}