package com.sergio.hotelsearch.adapter.output.kafka;

import com.sergio.hotelsearch.domain.model.Search;
import com.sergio.hotelsearch.domain.port.SearchRepositoryPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class KafkaSearchConsumerTest {

    @Mock
    private SearchRepositoryPort repository;

    @InjectMocks
    private KafkaSearchConsumer consumer;

    @Test
    void shouldPersistSearch() {

        Search search = new Search(
                "1",
                "hotel1",
                LocalDate.now(),
                LocalDate.now().plusDays(1),
                List.of(30)
        );

        consumer.consume(search);

        verify(repository).save(search);
    }
}