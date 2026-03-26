package com.sergio.hotelsearch.adapter.output.kafka;

import com.sergio.hotelsearch.adapter.output.kafka.dto.SearchMessageDTO;
import com.sergio.hotelsearch.domain.model.Search;
import com.sergio.hotelsearch.domain.port.SearchProducerPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaSearchProducer implements SearchProducerPort {

    private static final Logger log = LoggerFactory.getLogger(KafkaSearchProducer.class);

    private final KafkaTemplate<String, SearchMessageDTO> kafkaTemplate;
    private final String topic;

    public KafkaSearchProducer(KafkaTemplate<String, SearchMessageDTO> kafkaTemplate,
            @Value("${app.kafka.topic.searches}") String topic) {
        this.kafkaTemplate = kafkaTemplate;
        this.topic = topic;
    }

    @Override
    public void publishSearch(Search search) {

        SearchMessageDTO dto = new SearchMessageDTO(
                search.searchId(),
                search.hotelId(),
                search.checkIn(),
                search.checkOut(),
                search.ages());

        log.info("Publishing searchId={} to Kafka topic={}", dto.searchId(), topic);

        kafkaTemplate.send(topic, dto);

        log.debug("Search published successfully: {}", dto);
    }
}
