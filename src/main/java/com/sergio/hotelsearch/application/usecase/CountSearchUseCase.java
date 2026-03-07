package com.sergio.hotelsearch.application.usecase;

import com.sergio.hotelsearch.domain.port.SearchRepositoryPort;
import com.sergio.hotelsearc.domain.model.Search;
import org.springframework.stereotype.Service;

@Service
public class CountSearchUseCase {

    private final SearchRepositoryPort repository;

    public CountSearchUseCase(SearchRepositoryPort repository) {
        this.repository = repository;
    }

    public long execute(String searchId) {

        Search search = repository.findBySearchId(searchId)
                .orElseThrow(() -> new RuntimeException("Search not found"));

        return repository.countBySearch(search);
    }
}
