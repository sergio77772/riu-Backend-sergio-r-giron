package com.sergio.hotelsearch.adapter.output.kafka;


import com.sergio.hotelsearch.domain.port.SearchProducerPort;
import com.sergio.hotelsearch.domain.model.Search;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaSearchProducer implements SearchProducerPort {

    private final KafkaTemplate<String, Search> kafkaTemplate;

    public KafkaSearchProducer(KafkaTemplate<String, Search> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void publishSearch(Search search) {

        kafkaTemplate.send("hotel_availability_searches", search);
    }
}
