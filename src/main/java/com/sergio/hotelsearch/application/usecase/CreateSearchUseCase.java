package com.sergio.hotelsearch.application.usecase;

import com.sergio.hotelsearch.domain.port.SearchProducerPort;
import com.sergio.hotelsearch.domain.model.Search;
import org.springframework.stereotype.Service;

/**
 * Use case responsible for creating a hotel search and publishing it to Kafka.
 * Generates the searchId without database access (UUID).
 */
@Service
public class CreateSearchUseCase {

    private final SearchProducerPort searchProducerPort;

    public CreateSearchUseCase(SearchProducerPort searchProducerPort) {
        this.searchProducerPort = searchProducerPort;
    }

    public String execute(Search search) {

        searchProducerPort.publishSearch(search);

        return search.searchId();
    }
}