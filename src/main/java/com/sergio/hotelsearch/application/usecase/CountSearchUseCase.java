package com.sergio.hotelsearch.application.usecase;

import com.sergio.hotelsearch.domain.port.SearchRepositoryPort;
import com.sergio.hotelsearch.domain.model.Search;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class CountSearchUseCase {

    private static final Logger log = LoggerFactory.getLogger(CountSearchUseCase.class);

    private final SearchRepositoryPort repository;

    public CountSearchUseCase(SearchRepositoryPort repository) {
        this.repository = repository;
    }

    public long execute(String searchId) {

        log.info("Counting searches for searchId={}", searchId);

        Search search = repository.findBySearchId(searchId)
                .orElseThrow(() ->
                        new IllegalArgumentException("Search not found for id=" + searchId));

        long count = repository.countBySearch(search);

        log.info("Found {} searches for searchId={}", count, searchId);

        return count;
    }
}