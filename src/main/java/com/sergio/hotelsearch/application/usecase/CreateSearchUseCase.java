package com.sergio.hotelsearch.application.usecase;

import com.sergio.hotelsearch.domain.port.SearchProducerPort;
import com.sergio.hotelsearch.domain.model.Search;
import org.springframework.stereotype.Service;

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