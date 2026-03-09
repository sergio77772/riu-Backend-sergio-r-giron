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

import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class KafkaSearchConsumerTest {

    @Mock
    private SearchRepositoryPort repository;

    @InjectMocks
    private KafkaSearchConsumer consumer;

    @Test
    void shouldPersistSearch() throws InterruptedException {

        Search search = new Search(
                "1",
                "hotel1",
                LocalDate.of(2025, 1, 10),
                LocalDate.of(2025, 1, 12),
                List.of(30));

        consumer.consume(search);
        verify(repository, timeout(1000)).save(search);
    }
}
