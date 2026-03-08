package com.sergio.hotelsearch.adapter.output.kafka;

import com.sergio.hotelsearch.domain.port.SearchProducerPort;
import com.sergio.hotelsearch.domain.model.Search;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaSearchProducer implements SearchProducerPort {

    private static final Logger log = LoggerFactory.getLogger(KafkaSearchProducer.class);

    private static final String TOPIC = "hotel_availability_searches";

    private final KafkaTemplate<String, Search> kafkaTemplate;

    public KafkaSearchProducer(KafkaTemplate<String, Search> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void publishSearch(Search search) {

        log.info("Publishing searchId={} to Kafka topic={}", search.searchId(), TOPIC);

        kafkaTemplate.send(TOPIC, search);

        log.debug("Search published successfully: {}", search);
    }
}