package com.sergio.hotelsearch.application.usecase;

import com.sergio.hotelsearch.domain.model.Search;
import com.sergio.hotelsearch.domain.port.SearchProducerPort;
import org.springframework.stereotype.Service;

import java.util.UUID;

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

    public String execute(CreateSearchCommand command) {

        String searchId = UUID.randomUUID().toString();

        Search search = new Search(
                searchId,
                command.hotelId(),
                command.checkIn(),
                command.checkOut(),
                command.ages());

        searchProducerPort.publishSearch(search);

        return searchId;
    }
}
