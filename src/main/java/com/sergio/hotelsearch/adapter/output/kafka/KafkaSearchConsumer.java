package com.sergio.hotelsearch.adapter.output.kafka;

import com.sergio.hotelsearch.adapter.output.kafka.dto.SearchMessageDTO;
import com.sergio.hotelsearch.domain.model.Search;
import com.sergio.hotelsearch.domain.port.SearchRepositoryPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaSearchConsumer {

    private static final Logger log = LoggerFactory.getLogger(KafkaSearchConsumer.class);

    private final SearchRepositoryPort repository;

    public KafkaSearchConsumer(SearchRepositoryPort repository) {
        this.repository = repository;
    }

    @KafkaListener(topics = "${app.kafka.topic.searches}")
    public void consume(SearchMessageDTO dto) {
        log.info("Consumed searchId={} from Kafka", dto.searchId());
        persist(dto);
    }

    private void persist(SearchMessageDTO dto) {
        try {
            Search search = new Search(
                    dto.searchId(),
                    dto.hotelId(),
                    dto.checkIn(),
                    dto.checkOut(),
                    dto.ages());
            repository.save(search);
            log.debug("Search {} persisted successfully", dto.searchId());
        } catch (Exception e) {
            log.error("Error persisting search {}", dto.searchId(), e);
            throw e;
        }
    }
}
