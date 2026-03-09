package com.sergio.hotelsearch.application.usecase;

import com.sergio.hotelsearch.domain.model.Search;
import com.sergio.hotelsearch.domain.port.SearchProducerPort;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class CreateSearchUseCaseTest {

    @Mock
    private SearchProducerPort producer;

    @InjectMocks
    private CreateSearchUseCase useCase;

    @Test
    void shouldPublishSearch() {

        Search search = new Search(
                "123",
                "hotel1",
                LocalDate.now(),
                LocalDate.now().plusDays(2),
                List.of(30)
        );

        String id = useCase.execute(search);

        verify(producer).publishSearch(search);
        assertEquals("123", id);
    }
}