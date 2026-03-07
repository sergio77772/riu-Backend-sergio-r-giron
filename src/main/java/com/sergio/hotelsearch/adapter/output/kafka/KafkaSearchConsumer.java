package com.sergio.hotelsearch.adapter.output.kafka;
import com.sergio.hotelsearc.domain.model.Search;
import com.sergio.hotelsearch.domain.port.SearchRepositoryPort;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaSearchConsumer {

    private final SearchRepositoryPort repository;

    public KafkaSearchConsumer(SearchRepositoryPort repository) {
        this.repository = repository;
    }

    @KafkaListener(topics = "hotel_availability_searches")
    public void consume(Search search) {

        repository.save(search);
    }
}
