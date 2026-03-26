package com.sergio.hotelsearch.adapter.output.kafka;

import com.sergio.hotelsearch.adapter.output.kafka.dto.SearchMessageDTO;
import com.sergio.hotelsearch.domain.model.Search;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class KafkaSearchProducerTest {

    @Mock
    private KafkaTemplate<String, SearchMessageDTO> kafkaTemplate;

    private KafkaSearchProducer producer;

    @BeforeEach
    void setUp() {
        producer = new KafkaSearchProducer(kafkaTemplate, "hotel_availability_searches");
    }

    @Test
    void shouldSendMessageToKafka() {

        Search search = new Search(
                "1",
                "hotel1",
                LocalDate.now(),
                LocalDate.now().plusDays(1),
                List.of(30));

        producer.publishSearch(search);

        SearchMessageDTO expectedDto = new SearchMessageDTO(
                "1", "hotel1",
                LocalDate.now(),
                LocalDate.now().plusDays(1),
                List.of(30));

        verify(kafkaTemplate).send(eq("hotel_availability_searches"), eq(expectedDto));
    }
}
