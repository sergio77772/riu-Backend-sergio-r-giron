package com.sergio.hotelsearch.adapter.output.kafka;

import com.sergio.hotelsearch.domain.model.Search;
import com.sergio.hotelsearch.domain.port.SearchRepositoryPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaSearchConsumer {

    private static final Logger log = LoggerFactory.getLogger(KafkaSearchConsumer.class);

    private static final String TOPIC = "hotel_availability_searches";

    private final SearchRepositoryPort repository;

    public KafkaSearchConsumer(SearchRepositoryPort repository) {
        this.repository = repository;
    }

    @KafkaListener(topics = TOPIC)
    public void consume(Search search) {

        log.info("Consumed searchId={} from Kafka", search.searchId());

        try {
            repository.save(search);

            log.debug("Search {} persisted successfully", search.searchId());

        } catch (Exception e) {
            log.error("Error persisting search {}", search.searchId(), e);
            throw e;
        }
    }
}