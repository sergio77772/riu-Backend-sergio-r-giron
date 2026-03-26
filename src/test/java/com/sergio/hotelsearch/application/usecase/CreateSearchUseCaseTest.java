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
import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class CreateSearchUseCaseTest {

    @Mock
    private SearchProducerPort producer;

    @InjectMocks
    private CreateSearchUseCase useCase;

    @Test
    void shouldPublishSearch() {

        CreateSearchCommand command = new CreateSearchCommand(
                "hotel1",
                LocalDate.now(),
                LocalDate.now().plusDays(2),
                List.of(30));

        String id = useCase.execute(command);

        verify(producer).publishSearch(any(Search.class));
        assertNotNull(id);
    }
}
