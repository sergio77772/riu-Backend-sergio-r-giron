package com.sergio.hotelsearch.adapter.output.kafka;

import com.sergio.hotelsearch.domain.model.Search;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class KafkaSearchProducerTest {

    @Mock
    private KafkaTemplate<String, Search> kafkaTemplate;

    @InjectMocks
    private KafkaSearchProducer producer;

    @Test
    void shouldSendMessageToKafka() {

        Search search = new Search(
                "1",
                "hotel1",
                LocalDate.now(),
                LocalDate.now().plusDays(1),
                List.of(30)
        );

        producer.publishSearch(search);

        verify(kafkaTemplate).send("hotel_availability_searches", search);
    }
}
