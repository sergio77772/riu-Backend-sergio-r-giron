package com.sergio.hotelsearch.adapter.output.kafka;

import com.sergio.hotelsearch.adapter.output.kafka.dto.SearchMessageDTO;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.support.TaskExecutorAdapter;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;

import java.util.concurrent.Executors;

@Configuration
public class KafkaConfig {

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, SearchMessageDTO> kafkaListenerContainerFactory(
            ConsumerFactory<String, SearchMessageDTO> consumerFactory) {

        ConcurrentKafkaListenerContainerFactory<String, SearchMessageDTO> factory = new ConcurrentKafkaListenerContainerFactory<>();

        factory.setConsumerFactory(consumerFactory);

        factory.getContainerProperties()
                .setListenerTaskExecutor(
                        new TaskExecutorAdapter(Executors.newVirtualThreadPerTaskExecutor()));

        return factory;
    }
}
